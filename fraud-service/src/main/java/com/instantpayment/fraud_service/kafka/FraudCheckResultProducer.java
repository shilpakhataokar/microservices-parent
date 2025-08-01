package com.instantpayment.fraud_service.kafka;


import com.instantpayment.fraud_service.dto.FraudCheckResultXml;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FraudCheckResultProducer {
    private final KafkaTemplate<String, FraudCheckResultXml> kafkaTemplate;

    public FraudCheckResultProducer(KafkaTemplate<String, FraudCheckResultXml> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendFraudCheckResult(FraudCheckResultXml result) {
        kafkaTemplate.send("fraud.check.results", result);
    }
}