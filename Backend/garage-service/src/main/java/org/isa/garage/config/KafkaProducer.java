package org.isa.garage.config;

import org.isa.garage.dto.BookingConfirmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, BookingConfirmDTO> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, BookingConfirmDTO> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(BookingConfirmDTO bookingConfirmDTO){
        kafkaTemplate.send("BOOKING_STATUS",bookingConfirmDTO);
    }
}
