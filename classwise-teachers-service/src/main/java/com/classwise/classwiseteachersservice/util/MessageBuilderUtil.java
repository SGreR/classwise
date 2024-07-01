package com.classwise.classwiseteachersservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class MessageBuilderUtil {

    public static Message buildMessage(String topic, String eventType, Object payload){
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return MessageBuilder
                .withPayload(json)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader("event-type", eventType)
                .build();
    }
}
