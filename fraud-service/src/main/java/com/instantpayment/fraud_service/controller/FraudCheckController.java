package com.instantpayment.fraud_service.controller;

import com.instantpayment.fraud_service.dto.FraudCheckResultXml;
import com.instantpayment.fraud_service.dto.PaymentRequest;
import com.instantpayment.fraud_service.service.FraudCheckService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fraud-check")
@Validated
public class FraudCheckController {

    private final FraudCheckService fraudCheckService;

    public FraudCheckController(FraudCheckService fraudCheckService) {
        this.fraudCheckService = fraudCheckService;
    }

    @PostMapping(
            value = "/request",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public FraudCheckResultXml checkFraud(@Valid @RequestBody PaymentRequest request) {
        return fraudCheckService.checkFraud(request);
    }
}
