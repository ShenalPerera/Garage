package org.isa.garage.config;

import org.isa.garage.dto.BookingStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, BookingStatusDTO> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, BookingStatusDTO> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(BookingStatusDTO bookingStatusDTO){
        kafkaTemplate.send("BOOKING_STATUS", bookingStatusDTO);
    }
}
