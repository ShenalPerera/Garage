package org.isa.garage.service;

import org.isa.garage.dto.*;
import org.isa.garage.entity.Booking;
import org.isa.garage.entity.GarageService;
import org.isa.garage.entity.Schedule;
import org.isa.garage.exception.InvalidScheduleException;
import org.isa.garage.repository.ScheduleRepository;
import org.isa.garage.util.MappingUtils;
import org.isa.garage.util.ScheduleUtilsService;
import org.isa.garage.util.ValidatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleRepository scheduleRepository;
    private final GarageServicesHandlerService garageServicesHandlerService;
    private final ValidatorUtils validatorUtils;
    private final BookingService bookingService;

    private final KafkaProducerService kafkaProducerService;

    public ScheduleService(ScheduleRepository scheduleRepository, GarageServicesHandlerService garageServicesHandlerService, ValidatorUtils validatorUtils, BookingService bookingService,KafkaProducerService kafkaProducerService) {
        this.scheduleRepository = scheduleRepository;
        this.garageServicesHandlerService = garageServicesHandlerService;
        this.validatorUtils = validatorUtils;
        this.bookingService = bookingService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ScheduleDTO> getAllSchedules(LocalDate date) {
        return scheduleRepository.findALLByDateEqualsOrderByStartTime(java.sql.Date.valueOf(date)).stream().map(MappingUtils::mapScheduleToDTO).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ScheduleDTO createSingleServiceSchedule(SingleServiceScheduleCreateDTO singleServiceScheduleCreateDTO) {
        validatorUtils.validateScheduleStartTimeAndEndTime(singleServiceScheduleCreateDTO.getStartTime(), singleServiceScheduleCreateDTO.getEndTime());


        GarageService service = garageServicesHandlerService.getServiceById(singleServiceScheduleCreateDTO.getServiceId());
        validatorUtils.validateScheduleServices(singleServiceScheduleCreateDTO.getStartTime(), singleServiceScheduleCreateDTO.getEndTime(), service.getDuration());

        Schedule schedule = new Schedule();
        schedule.setStartTime(singleServiceScheduleCreateDTO.getStartTime());
        schedule.setEndTime(singleServiceScheduleCreateDTO.getEndTime());
        schedule.setDate(singleServiceScheduleCreateDTO.getDate());
        schedule.setGarageServices(List.of(service));
        schedule.setMaxCapacity(1);
        schedule.setCurrentCapacity(1);

        return MappingUtils.mapScheduleToDTO(scheduleRepository.save(schedule));

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Integer createMultiServiceSchedule(MultiServiceScheduleCreateDTO multiServiceScheduleCreateDTO) {
        Integer conflictCount = getConflictSchedulesCountWithCurrentSchedule(multiServiceScheduleCreateDTO.getDate(), multiServiceScheduleCreateDTO.getStartTime(), multiServiceScheduleCreateDTO.getEndTime());

        if (null != conflictCount && conflictCount > 0)
            throw new InvalidScheduleException("Schedules conflict with existing schedules. check again");

        validatorUtils.validateScheduleStartTimeAndEndTime(multiServiceScheduleCreateDTO.getStartTime(), multiServiceScheduleCreateDTO.getEndTime());
        validatorUtils.validateScheduleRecurrence(multiServiceScheduleCreateDTO.getIsRecurrent(), multiServiceScheduleCreateDTO.getPeriod());


        if (multiServiceScheduleCreateDTO.getServiceId().isEmpty()) {
            throw new InvalidScheduleException("Invalid service Ids.");
        }

        List<GarageService> services = garageServicesHandlerService.getServicesByIdList(multiServiceScheduleCreateDTO.getServiceId());

        if (services.isEmpty()) throw new InvalidScheduleException("Services cannot be find for give schedule");

        Integer maxDuration = garageServicesHandlerService.getMaxServiceDurationFromGivenIds(multiServiceScheduleCreateDTO.getServiceId());
        validatorUtils.validateScheduleServices(multiServiceScheduleCreateDTO.getStartTime(), multiServiceScheduleCreateDTO.getEndTime(), maxDuration);

        if (null != multiServiceScheduleCreateDTO.getIsRecurrent() && multiServiceScheduleCreateDTO.getIsRecurrent()) {
            List<Schedule> schedules = ScheduleUtilsService.createRecurrentSchedules(multiServiceScheduleCreateDTO, services);
            List<Schedule> createdSchedules =  scheduleRepository.saveAll(schedules);
            List<ScheduleDTO>  scheduleDTOS = createdSchedules.stream().map(MappingUtils::mapScheduleToDTO).collect(Collectors.toList());
            kafkaProducerService.sendCreatedSchedules(scheduleDTOS);
            return schedules.size();
        } else {
            Schedule schedule = ScheduleUtilsService.createNoneRecurrentSchedule(multiServiceScheduleCreateDTO, services);
            ScheduleDTO scheduleDTO= MappingUtils.mapScheduleToDTO(scheduleRepository.save(schedule)) ;
            kafkaProducerService.sendCreatedSchedules(List.of(scheduleDTO));
            return 1;
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ScheduleDTO editSchedule(ScheduleEditDTO scheduleEditDTO){
        List<BookingDTO> bookings = bookingService.getBookingsByScheduleID(scheduleEditDTO.getId());
        if (!bookings.isEmpty())
            throw new InvalidScheduleException("Schedule have bookings. Can not edit");

        validatorUtils.validateScheduleStartTimeAndEndTime(scheduleEditDTO.getStartTime(), scheduleEditDTO.getEndTime());
        List<GarageService> services = garageServicesHandlerService.getServicesByIdList(scheduleEditDTO.getServiceId());

        if (services.isEmpty()) throw new InvalidScheduleException("Services cannot be find for given schedule");

        Integer maxDuration = garageServicesHandlerService.getMaxServiceDurationFromGivenIds(scheduleEditDTO.getServiceId());
        validatorUtils.validateScheduleServices(scheduleEditDTO.getStartTime(), scheduleEditDTO.getEndTime(), maxDuration);
        Schedule schedule = scheduleRepository.findById(scheduleEditDTO.getId()).orElseThrow(()->  new InvalidScheduleException("Schedule not found"));

        schedule.setStartTime(scheduleEditDTO.getStartTime());
        schedule.setEndTime(scheduleEditDTO.getEndTime());
        schedule.setDate(scheduleEditDTO.getDate());
        schedule.setMaxCapacity(scheduleEditDTO.getMaxCapacity());
        schedule.setGarageServices(services);

        ScheduleDTO scheduleDTO =  MappingUtils.mapScheduleToDTO(scheduleRepository.save(schedule));
        kafkaProducerService.sendEditSchedule(scheduleDTO);
        return scheduleDTO;
    }

    public void deleteSchedule(Integer scheduleId){
        Schedule  schedule = scheduleRepository.findById(scheduleId).orElseThrow(()->new InvalidScheduleException("Schedule Not found!"));
        if (!schedule.getBookings().isEmpty())
            throw new InvalidScheduleException("Schedule have booking. Can not delete!");
        for(GarageService garageService: schedule.getGarageServices()){
            garageService.getSchedules().remove(schedule);
        }
        schedule.getGarageServices().clear();
        scheduleRepository.delete(schedule);
        kafkaProducerService.sendDeleteSchedule(MappingUtils.mapScheduleToDTO(schedule));
    }

    public Integer getConflictSchedulesCountWithCurrentSchedule(Date creationDate, Time startTime, Time endTime) {
        return scheduleRepository.findConflictingSchedules(creationDate, startTime, endTime);
    }

    public Long getCount(){
        return scheduleRepository.count();
    }
}
