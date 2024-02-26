package com.example.userdemo.controller;

import com.example.userdemo.dto.BookingDTO;
import com.example.userdemo.service.KafkaProducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockEventPublishController {

    private final KafkaProducerService kafkaProducerService;

    public MockEventPublishController(KafkaProducerService kafkaProducerService){
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/trigger-event/booking-create")
    public void triggerBookingCreateEvent(@RequestBody BookingDTO bookingDTO){
        System.out.println(bookingDTO);
        kafkaProducerService.sendBookingCreate(bookingDTO);
    }
}
