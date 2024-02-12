package org.isa.garage.service;

import org.isa.garage.dto.ScheduleDTO;
import org.isa.garage.dto.SingleServiceScheduleCreateDTO;
import org.isa.garage.entity.GarageService;
import org.isa.garage.entity.Schedule;
import org.isa.garage.exception.InvalidScheduleTimesException;
import org.isa.garage.repository.ScheduleRepository;
import org.isa.garage.util.MappingUtils;
import org.isa.garage.util.ValidatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleRepository scheduleRepository;
    private final GarageServicesHandlerService garageServicesHandlerService;
    private final ValidatorUtils validatorUtils;
    public ScheduleService(ScheduleRepository scheduleRepository, GarageServicesHandlerService garageServicesHandlerService, ValidatorUtils validatorUtils){
        this.scheduleRepository = scheduleRepository;
        this.garageServicesHandlerService = garageServicesHandlerService;
        this.validatorUtils = validatorUtils;
    }

    public List<ScheduleDTO> getAllSchedules(LocalDate date){
        return scheduleRepository.findALLByDateEquals(java.sql.Date.valueOf(date))
                .stream()
                .parallel()
                .map(MappingUtils::mapScheduleToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ScheduleDTO createSingleServiceSchedule(SingleServiceScheduleCreateDTO singleServiceScheduleCreateDTO) {
        validatorUtils.validateScheduleStartTimeAndEndTime(singleServiceScheduleCreateDTO.getStartTime(), singleServiceScheduleCreateDTO.getEndTime());

        Schedule schedule = new Schedule();
        schedule.setStartTime(singleServiceScheduleCreateDTO.getStartTime());
        schedule.setEndTime(singleServiceScheduleCreateDTO.getEndTime());
        schedule.setDate(singleServiceScheduleCreateDTO.getDate());

        GarageService services = garageServicesHandlerService.getServiceById(singleServiceScheduleCreateDTO.getServiceId());

        schedule.setGarageServices(List.of(services));
        schedule.setMaxCapacity(1);
        schedule.setCurrentCapacity(1);

        return MappingUtils.mapScheduleToDTO(scheduleRepository.save(schedule));

    }

}
