package com.instantpayment.payment_service.service;

import com.instantpayment.payment_service.dto.FraudCheckResult;
import com.instantpayment.payment_service.dto.PaymentRequest;
import com.instantpayment.payment_service.util.PaymentValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PaymentProcessingServiceTest {

    @InjectMocks
    private PaymentProcessingService paymentService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PaymentValidator paymentValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentService.brokerFraudCheckUrl = "http://broker/fraud-check";
    }

    @Test
    void shouldRejectInvalidPayment() {
        PaymentRequest request = new PaymentRequest();
        when(paymentValidator.validate(request)).thenReturn("Invalid payment");

        FraudCheckResult result = paymentService.processPayment(request);

        assertThat(result.getStatus()).isEqualTo("REJECTED");
        assertThat(result.getMessage()).isEqualTo("Invalid payment");
    }

    @Test
    void shouldReturnErrorIfBrokerResponseIsNull() {
        PaymentRequest request = new PaymentRequest();
        when(paymentValidator.validate(request)).thenReturn(PaymentValidator.VALID_PAYMENT);
        when(restTemplate.postForEntity(anyString(), any(), eq(FraudCheckResult.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        FraudCheckResult result = paymentService.processPayment(request);

        assertThat(result.getStatus()).contains("INTERNAL_SERVER_ERROR");
        assertThat(result.getMessage()).isEqualTo("No response from Broker System");
    }

    @Test
    void shouldReturnFraudCheckResultFromBroker() {
        PaymentRequest request = new PaymentRequest();
        when(paymentValidator.validate(request)).thenReturn(PaymentValidator.VALID_PAYMENT);

        FraudCheckResult brokerResult = new FraudCheckResult();
        brokerResult.setTransactionId("tx123");
        brokerResult.setStatus("APPROVED");
        brokerResult.setMessage("OK");

        when(restTemplate.postForEntity(anyString(), any(), eq(FraudCheckResult.class)))
                .thenReturn(new ResponseEntity<>(brokerResult, HttpStatus.OK));

        FraudCheckResult result = paymentService.processPayment(request);

        assertThat(result.getStatus()).isEqualTo("APPROVED");
        assertThat(result.getMessage()).isEqualTo("OK");
        assertThat(result.getTransactionId()).isEqualTo("tx123");
    }
}