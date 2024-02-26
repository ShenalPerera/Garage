package org.isa.garage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.isa.garage.config.kafkaConfig.KafkaTopics;
import org.isa.garage.dto.BookingCreateDTO;
import org.isa.garage.dto.BookingDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {

    private final ObjectMapper objectMapper;
    private final BookingService bookingService;

    private KafkaListenerService(ObjectMapper objectMapper, BookingService bookingService){
        this.objectMapper = objectMapper;
        this.bookingService = bookingService;
    }

    @KafkaListener(topics = KafkaTopics.BOOKING_CREATE, groupId = "groupID")
    public void createBookingListener(String bookingCreateString){
        try{
            BookingDTO bookingCreateDTO =  objectMapper.readValue(bookingCreateString, BookingDTO.class);
            bookingService.createBooking(bookingCreateDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
