package org.isa.garage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.isa.garage.dto.BookingCreateDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenService {

    private final ObjectMapper objectMapper;
    private final BookingService bookingService;

    private KafkaListenService(ObjectMapper objectMapper, BookingService bookingService){
        this.objectMapper = objectMapper;
        this.bookingService = bookingService;
    }

    @KafkaListener(topics = "BOOKING_CREATE", groupId = "groupID")
    public void createBookingListener(String bookingCreateString){
        try{
            BookingCreateDTO bookingCreateDTO =  objectMapper.readValue(bookingCreateString, BookingCreateDTO.class);
            bookingService.createBooking(bookingCreateDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
