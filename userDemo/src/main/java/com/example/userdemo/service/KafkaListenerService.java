package com.example.userdemo.service;

import com.example.userdemo.config.KafkaTopics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {

    @KafkaListener(topics = KafkaTopics.BOOKING_STATUS, groupId = "groupID")
    public void bookingStatusUpdateListener(String bookingStatus){
        System.out.println("Received booking status : " +bookingStatus);
    }

    @KafkaListener(topics = KafkaTopics.SCHEDULES_CREATE,groupId = "groupID")
    public void scheduleCreateListener(String schedleCreateString){
        System.out.println("Received schedule create : " + schedleCreateString);
    }

    @KafkaListener(topics = KafkaTopics.SCHEDULE_EDIT, groupId = "groupID")
    public void scheduleEditListener(String scheduleEditString){
        System.out.println("Received schedule edit: " + scheduleEditString);
    }

    @KafkaListener(topics = KafkaTopics.SCHEDULE_DELETE, groupId = "groupID")
    public void scheduleDeleteListener(String scheduleDeleteString){
        System.out.println("Received schedule delete: " + scheduleDeleteString);
    }
}
