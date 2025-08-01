package com.instantpayment.fraud_service.kafka;

import com.instantpayment.fraud_service.dto.PaymentRequest;
import com.instantpayment.fraud_service.service.FraudCheckService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FraudCheckRequestConsumer {
    private final FraudCheckService fraudService;

    public FraudCheckRequestConsumer(FraudCheckService fraudService) {
        this.fraudService = fraudService;
    }

    @KafkaListener(topics = "fraud.check.requests", groupId = "fraud-service")
    public void listen(PaymentRequest request) {
        fraudService.checkFraud(request);
    }
}