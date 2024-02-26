package org.isa.garage.service;

import org.isa.garage.config.kafkaConfig.KafkaTopics;
import org.isa.garage.dto.BookingDTO;
import org.isa.garage.dto.ScheduleDTO;
import org.isa.garage.dto.ScheduleEditDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, BookingDTO> kafkaTemplate;
    private final KafkaTemplate<String, List<ScheduleDTO>> schedulesCreateKafkaTemplate;
    private final KafkaTemplate<String, ScheduleDTO> scheduleDTOKafkaTemplate;


    @Autowired
    public KafkaProducerService(KafkaTemplate<String, BookingDTO> kafkaTemplate, KafkaTemplate<String,List<ScheduleDTO>> schedulesCreateKafkaTemplate, KafkaTemplate<String, ScheduleDTO> scheduleDTOKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.schedulesCreateKafkaTemplate = schedulesCreateKafkaTemplate;
        this.scheduleDTOKafkaTemplate = scheduleDTOKafkaTemplate;
    }
    public void sendBookingStatus(BookingDTO bookingDTO){
        kafkaTemplate.send(KafkaTopics.BOOKING_STATUS, bookingDTO);
    }

    public void sendCreatedSchedules(List<ScheduleDTO> scheduleDTOList){
        schedulesCreateKafkaTemplate.send(KafkaTopics.SCHEDULES_CREATE, scheduleDTOList);
    }

    public void sendDeleteSchedule(ScheduleDTO scheduleDTO){
        scheduleDTOKafkaTemplate.send(KafkaTopics.SCHEDULE_DELETE,scheduleDTO);
    }

    public void sendEditSchedule(ScheduleDTO scheduleDTO){
        scheduleDTOKafkaTemplate.send(KafkaTopics.SCHEDULE_EDIT,scheduleDTO);
    }

}
