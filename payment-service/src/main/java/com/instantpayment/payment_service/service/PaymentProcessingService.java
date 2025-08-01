package com.instantpayment.payment_service.service;

import com.instantpayment.payment_service.dto.FraudCheckResult;
import com.instantpayment.payment_service.dto.PaymentRequest;
import com.instantpayment.payment_service.util.PaymentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentProcessingService {

    @Value("${bs.fraud-check.url}")
    String brokerFraudCheckUrl;
    /* 1. Receives JSON payment request via REST

    2. Validates:     UUID transaction ID
                      ISO country code (payer/payee)
                      ISO currency code
                      ISO date formats
    3. Sends JSON payment to BS via REST
    */
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PaymentValidator paymentValidator;

    public FraudCheckResult processPayment(PaymentRequest paymentRequest) {

        FraudCheckResult fraudCheckResult = new FraudCheckResult();
        fraudCheckResult.setTransactionId(paymentRequest.getTransactionId());
        // 1. Validate payment request
        String validationMessage = paymentValidator.validate(paymentRequest);
        if (!validationMessage.equals(PaymentValidator.VALID_PAYMENT)) {

            fraudCheckResult.setStatus("REJECTED");
            fraudCheckResult.setMessage(validationMessage);
            return fraudCheckResult;
        }

        // 2. Call Broker System for fraud check
        ResponseEntity<FraudCheckResult> fraudResponse = restTemplate.postForEntity(
                brokerFraudCheckUrl, paymentRequest, FraudCheckResult.class);

        if (fraudResponse.getBody() == null) {
            fraudCheckResult.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            fraudCheckResult.setMessage("No response from Broker System");
            return fraudCheckResult;
        }

        return fraudResponse.getBody();
    }

}
