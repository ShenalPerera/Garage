package com.example.userdemo.service;

import com.example.userdemo.config.KafkaTopics;
import com.example.userdemo.dto.BookingDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, BookingDTO> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, BookingDTO> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookingCreate(BookingDTO bookingDTO){
        kafkaTemplate.send(KafkaTopics.BOOKING_CREATE,bookingDTO);
    }
}
