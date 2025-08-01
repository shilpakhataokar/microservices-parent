package com.instantpayment.payment_service.kafka;


import com.instantpayment.payment_service.dto.PaymentRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FraudCheckRequestProducer {
    private final KafkaTemplate<String, PaymentRequest> kafkaTemplate;

    public FraudCheckRequestProducer(KafkaTemplate<String, PaymentRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendFraudCheckRequest(PaymentRequest request) {
        kafkaTemplate.send("fraud.check.requests", request);
    }
}