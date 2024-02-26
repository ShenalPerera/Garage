package org.isa.garage.controller;

import org.isa.garage.dto.BookingCreateDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingCreateController {

    private final KafkaTemplate<String, BookingCreateDTO> kafkaTemplate;

    public BookingCreateController(KafkaTemplate<String, BookingCreateDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/bookings/mock-send-booking")
    public void mockTheDemoBooking(@RequestBody BookingCreateDTO bookingCreateDTO) {
        kafkaTemplate.send("BOOKING_CREATE", bookingCreateDTO);
    }
}
