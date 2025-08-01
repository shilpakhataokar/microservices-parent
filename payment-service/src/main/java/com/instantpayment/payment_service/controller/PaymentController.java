package com.instantpayment.payment_service.controller;

import com.instantpayment.payment_service.dto.FraudCheckResult;
import com.instantpayment.payment_service.dto.PaymentRequest;
import com.instantpayment.payment_service.service.PaymentProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentProcessingService paymentService;
    @Value("${bs.fraud-check.url}")
    private String brokerFraudCheckUrl;


    @PostMapping("/processPayment")
    @ResponseStatus(HttpStatus.CREATED)
    public FraudCheckResult processPayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.processPayment(paymentRequest);
    }

}
