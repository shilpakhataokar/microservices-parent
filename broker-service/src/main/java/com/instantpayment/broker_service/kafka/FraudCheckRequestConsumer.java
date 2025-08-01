package com.instantpayment.broker_service.kafka;

import com.instantpayment.broker_service.dto.PaymentRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FraudCheckRequestConsumer {
    private final KafkaTemplate<String, PaymentRequest> kafkaTemplate;

    public FraudCheckRequestConsumer(KafkaTemplate<String, PaymentRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "fraud.check.requests", groupId = "broker-service")
    public void listen(PaymentRequest request) {
        kafkaTemplate.send("fraud.check.requests", request); // forward to fraud-service
    }
}
