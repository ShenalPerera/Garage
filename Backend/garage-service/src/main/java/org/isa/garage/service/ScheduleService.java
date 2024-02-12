package org.isa.garage.service;

import org.isa.garage.dto.ScheduleDTO;
import org.isa.garage.entity.Schedule;
import org.isa.garage.repository.ScheduleRepository;
import org.isa.garage.util.MappingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    public List<ScheduleDTO> getAllSchedules(LocalDate date){
        return scheduleRepository.findALLByDateEquals(java.sql.Date.valueOf(date))
                .stream()
                .parallel()
                .map(MappingUtils::mapScheduleToDTO)
                .collect(Collectors.toList());
    }

}
