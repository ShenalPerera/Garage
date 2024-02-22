package org.isa.garage.controller;

import org.isa.garage.dto.BookingCreateDTO;
import org.isa.garage.service.BookingService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingCreateController {

    private final BookingService bookingService;

    private final KafkaTemplate<String, BookingCreateDTO> kafkaTemplate;

    public BookingCreateController(BookingService bookingService, KafkaTemplate<String,BookingCreateDTO> kafkaTemplate){
        this.bookingService = bookingService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/bookings/mock-send-booking")
    public void mockTheDemoBooking(@RequestBody BookingCreateDTO bookingCreateDTO){
        kafkaTemplate.send("BOOKING_CREATE",bookingCreateDTO);
    }
}
