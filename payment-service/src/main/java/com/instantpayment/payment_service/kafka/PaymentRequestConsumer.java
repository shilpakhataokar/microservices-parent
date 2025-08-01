
package com.instantpayment.payment_service.kafka;


import com.instantpayment.payment_service.dto.PaymentRequest;
import com.instantpayment.payment_service.service.PaymentProcessingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentRequestConsumer {
    private final PaymentProcessingService paymentService;

    public PaymentRequestConsumer(PaymentProcessingService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "payments.requests", groupId = "payment-service")
    public void listen(PaymentRequest request) {
        paymentService.processPayment(request);
    }
}