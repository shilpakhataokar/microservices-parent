package com.instantpayment.broker_service.kafka;


import com.instantpayment.broker_service.dto.FraudCheckResult;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FraudCheckResultConsumer {
    private final KafkaTemplate<String, FraudCheckResult> kafkaTemplate;

    public FraudCheckResultConsumer(KafkaTemplate<String, FraudCheckResult> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "fraud.check.results", groupId = "broker-service")
    public void listen(FraudCheckResult result) {
        kafkaTemplate.send("fraud.check.results", result); // forward to payment-service
    }
}