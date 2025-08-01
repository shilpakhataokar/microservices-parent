package com.instantpayment.payment_service.kafka;

import com.instantpayment.payment_service.dto.FraudCheckResult;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FraudCheckResultConsumer {
    @KafkaListener(topics = "fraud.check.results", groupId = "payment-service")
    public void listen(FraudCheckResult result) {
        // handle fraud check result
    }
}