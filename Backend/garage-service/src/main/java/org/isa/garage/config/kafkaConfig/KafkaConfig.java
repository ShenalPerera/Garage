package org.isa.garage.config;

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
    public NewTopic topic2() {
        if (!topicExists()) {
            return TopicBuilder
                    .name("BOOKING_CREATE")
                    .build();
        }
        System.out.println("Topic already exists " + "TopicsNames.BOOKED_TIME_SLOTS");
        return null;
    }

    private boolean topicExists() {
        try (AdminClient adminClient = AdminClient.create(Map.of("bootstrap.servers", bootstrapServers))) {
            return adminClient.listTopics().names().get().contains("BOOKING_CREATE");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}