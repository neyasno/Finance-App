package org.example.authservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSenderService {

    @Value("${kafka.topics.password-recovery-topic}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Long userId, String message) {
        kafkaTemplate.send(topicName, userId.toString(), message);
    }
}
