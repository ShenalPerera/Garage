package org.isa.garage.config.kafkaConfig;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public NewTopic bookingCreate() {
        return createTopicIfNotExists(KafkaTopics.BOOKING_CREATE);
    }
    @Bean
    public NewTopic bookingStatus(){
        return createTopicIfNotExists(KafkaTopics.BOOKING_STATUS);
    }
    @Bean
    public NewTopic schedulesCreate(){
        return createTopicIfNotExists(KafkaTopics.SCHEDULES_CREATE);
    }

    @Bean
    public NewTopic scheduleDelete(){
        return createTopicIfNotExists(KafkaTopics.SCHEDULE_DELETE);
    }

    @Bean
    public NewTopic scheduleEdit(){
        return createTopicIfNotExists(KafkaTopics.SCHEDULE_EDIT);
    }

    private boolean topicExists(String topicName) {
        try (AdminClient adminClient = AdminClient.create(Map.of("bootstrap.servers", bootstrapServers))) {
            return adminClient.listTopics().names().get().contains(topicName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private NewTopic createTopicIfNotExists(String topicName) {
        if (!topicExists(topicName)) {
            return TopicBuilder
                    .name(topicName)
                    .build();
        }
        System.out.println("Topic already exists " + topicName);
        return null;
    }
}