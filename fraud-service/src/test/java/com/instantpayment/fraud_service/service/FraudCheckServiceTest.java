package com.instantpayment.fraud_service.service;

import com.instantpayment.fraud_service.dto.FraudCheckResultXml;
import com.instantpayment.fraud_service.dto.PaymentRequest;
import com.instantpayment.fraud_service.util.FraudDetectionProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

class FraudCheckServiceTest {

    @Mock
    private FraudDetectionProcessor fraudDetectionProcessor;

    @InjectMocks
    private FraudCheckService fraudCheckService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private PaymentRequest baseRequest() {
        PaymentRequest req = new PaymentRequest();
        req.setTransactionId("tx1");
        req.setPayerName("John");
        req.setPayeeName("Jane");
        req.setPayerCountryCode("US");
        req.setPayeeCountryCode("CA");
        req.setPayerBank("BankA");
        req.setPayeeBank("BankB");
        req.setPaymentInstruction("Test");
        return req;
    }

    @Test
    void shouldRejectBlacklistedName() {
        PaymentRequest req = baseRequest();
        req.setPayerName("Mark Imaginary");
        FraudCheckResultXml result = fraudCheckService.checkFraud(req);
        assertThat(result.getStatus()).isEqualTo("REJECTED");
        assertThat(result.getMessage()).contains("Payer Name");
    }

    @Test
    void shouldRejectBlacklistedCountry() {
        PaymentRequest req = baseRequest();
        req.setPayerCountryCode("CUB");
        FraudCheckResultXml result = fraudCheckService.checkFraud(req);
        assertThat(result.getStatus()).isEqualTo("REJECTED");
        assertThat(result.getMessage()).contains("Country Code");
    }

    @Test
    void shouldRejectBlacklistedBank() {
        PaymentRequest req = baseRequest();
        req.setPayerBank("BANK OF KUNLUN");
        FraudCheckResultXml result = fraudCheckService.checkFraud(req);
        assertThat(result.getStatus()).isEqualTo("REJECTED");
        assertThat(result.getMessage()).contains("Bank");
    }

    @Test
    void shouldRejectBlacklistedInstruction() {
        PaymentRequest req = baseRequest();
        req.setPaymentInstruction("Artillery Procurement");
        FraudCheckResultXml result = fraudCheckService.checkFraud(req);
        assertThat(result.getStatus()).isEqualTo("REJECTED");
        assertThat(result.getMessage()).contains("instruction");
    }

    @Test
    void shouldApproveNonBlacklistedRequest() {
        PaymentRequest req = baseRequest();
        FraudCheckResultXml result = fraudCheckService.checkFraud(req);
        assertThat(result.getStatus()).isEqualTo("APPROVED");
        assertThat(result.getMessage()).contains("all okay");
    }
}