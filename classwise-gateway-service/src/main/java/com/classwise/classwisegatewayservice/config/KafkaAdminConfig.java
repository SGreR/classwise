package com.classwise.classwisegatewayservice.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaAdminConfig {

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic teacherEventsTopic() {
        return new NewTopic("teacher-events", 3, (short) 1);
    }

    @Bean
    public NewTopic courseEventsTopic() {
        return new NewTopic("course-events", 3, (short) 1);
    }

    @Bean
    public NewTopic jointEventsTopic() {
        return new NewTopic("joint-events", 3, (short) 1);
    }
}
