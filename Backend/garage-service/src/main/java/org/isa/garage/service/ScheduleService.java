package org.isa.garage.service;

import org.isa.garage.controller.GarageRestController;
import org.isa.garage.entity.TimeSlot;
import org.isa.garage.repository.TimeSlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

@Service
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private final TimeSlotRepository timeSlotRepository;

    public ScheduleService(TimeSlotRepository timeSlotRepository){
        this.timeSlotRepository = timeSlotRepository;
    }

    public List<TimeSlot> testMethod(){
        List<TimeSlot> temp =  timeSlotRepository.findByStartTimeAfterAndEndTimeBefore(Time.valueOf("10:10:00"),Time.valueOf("19:00:00"));
        logger.info(temp.toString());
        return temp;

    }
}
