package org.isa.garage.util;

import org.isa.garage.dto.GarageServiceDTO;
import org.isa.garage.dto.MultiServiceScheduleCreateDTO;
import org.isa.garage.dto.ScheduleDTO;
import org.isa.garage.dto.ScheduleEditDTO;
import org.isa.garage.entity.GarageService;
import org.isa.garage.entity.Schedule;

import java.util.List;
import java.util.stream.Collectors;

public class MappingUtils {


    public static GarageServiceDTO mapServiceToDTO(GarageService service) {
        return new GarageServiceDTO(service.getId(), service.getServiceName(), service.getDuration());
    }

    public static ScheduleDTO mapScheduleToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setStartTime(schedule.getStartTime());
        scheduleDTO.setEndTime(schedule.getEndTime());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setMaxCapacity(schedule.getMaxCapacity());
        scheduleDTO.setCurrentCapacity(schedule.getCurrentCapacity());

        scheduleDTO.setGarageServices(schedule.getGarageServices().stream().parallel().map(MappingUtils::mapServiceToDTO).collect(Collectors.toList()));

        return scheduleDTO;
    }

    public static Schedule mapMultiServiceScheduleDTOToSchedule(MultiServiceScheduleCreateDTO multiServiceScheduleCreateDTO, List<GarageService> services) {
        Schedule schedule = new Schedule();
        schedule.setStartTime(multiServiceScheduleCreateDTO.getStartTime());
        schedule.setEndTime(multiServiceScheduleCreateDTO.getEndTime());
        schedule.setDate(multiServiceScheduleCreateDTO.getDate());
        schedule.setGarageServices(services);
        schedule.setMaxCapacity(multiServiceScheduleCreateDTO.getMaxCapacity());
        schedule.setCurrentCapacity(0);
        return schedule;
    }

    public static Schedule mapScheduleEditDTOToSchedule(ScheduleEditDTO scheduleEditDTO, List<GarageService> services) {
        return new Schedule(
                scheduleEditDTO.getId(),
                scheduleEditDTO.getStartTime(),
                scheduleEditDTO.getEndTime(),
                scheduleEditDTO.getDate(),
                scheduleEditDTO.getMaxCapacity(), 0,
                services
        );
    }

}
