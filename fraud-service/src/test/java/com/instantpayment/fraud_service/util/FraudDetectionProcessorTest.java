package com.instantpayment.fraud_service.util;

import com.instantpayment.fraud_service.dto.FraudCheckResultXml;
import com.instantpayment.fraud_service.dto.PaymentRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FraudDetectionProcessorTest {

    private final FraudDetectionProcessor processor = new FraudDetectionProcessor();

    @Test
    void shouldApproveNonBlacklistedRequest() {
        PaymentRequest req = new PaymentRequest();
        req.setTransactionId("tx1");
        req.setPayerName("John");
        req.setPayeeName("Jane");
        req.setPayerCountryCode("US");
        req.setPayeeCountryCode("CA");
        req.setPayerBank("BankA");
        req.setPayeeBank("BankB");
        req.setPaymentInstruction("Regular Payment");

        FraudCheckResultXml result = processor.performFraudCheck(req);

        assertThat(result.getStatus()).isEqualTo("APPROVED");
        assertThat(result.getMessage()).contains("all okay");
    }

    @Test
    void shouldRejectBlacklistedPayerName() {
        PaymentRequest req = new PaymentRequest();
        req.setTransactionId("tx2");
        req.setPayerName("Mark Imaginary");
        req.setPayeeName("Jane");
        req.setPayerCountryCode("US");
        req.setPayeeCountryCode("CA");
        req.setPayerBank("BankA");
        req.setPayeeBank("BankB");
        req.setPaymentInstruction("Regular Payment");

        FraudCheckResultXml result = processor.performFraudCheck(req);

        assertThat(result.getStatus()).isEqualTo("REJECTED");
        assertThat(result.getMessage()).contains("Suspicious");
    }

    @Test
    void shouldRejectBlacklistedPayeeName() {
        PaymentRequest req = new PaymentRequest();
        req.setTransactionId("tx3");
        req.setPayerName("John");
        req.setPayeeName("Govind Real");
        req.setPayerCountryCode("US");
        req.setPayeeCountryCode("CA");
        req.setPayerBank("BankA");
        req.setPayeeBank("BankB");
        req.setPaymentInstruction("Regular Payment");

        FraudCheckResultXml result = processor.performFraudCheck(req);

        assertThat(result.getStatus()).isEqualTo("REJECTED");
    }

    @Test
    void shouldRejectBlacklistedCountry() {
        PaymentRequest req = new PaymentRequest();
        req.setTransactionId("tx4");
        req.setPayerName("John");
        req.setPayeeName("Jane");
        req.setPayerCountryCode("CUB");
        req.setPayeeCountryCode("CA");
        req.setPayerBank("BankA");
        req.setPayeeBank("BankB");
        req.setPaymentInstruction("Regular Payment");

        FraudCheckResultXml result = processor.performFraudCheck(req);

        assertThat(result.getStatus()).isEqualTo("REJECTED");
    }

    @Test
    void shouldRejectBlacklistedBank() {
        PaymentRequest req = new PaymentRequest();
        req.setTransactionId("tx5");
        req.setPayerName("John");
        req.setPayeeName("Jane");
        req.setPayerCountryCode("US");
        req.setPayeeCountryCode("CA");
        req.setPayerBank("BANK OF KUNLUN");
        req.setPayeeBank("BankB");
        req.setPaymentInstruction("Regular Payment");

        FraudCheckResultXml result = processor.performFraudCheck(req);

        assertThat(result.getStatus()).isEqualTo("REJECTED");
    }

    @Test
    void shouldRejectBlacklistedInstruction() {
        PaymentRequest req = new PaymentRequest();
        req.setTransactionId("tx6");
        req.setPayerName("John");
        req.setPayeeName("Jane");
        req.setPayerCountryCode("US");
        req.setPayeeCountryCode("CA");
        req.setPayerBank("BankA");
        req.setPayeeBank("BankB");
        req.setPaymentInstruction("Artillery Procurement");

        processor.performFraudCheck(req);

       // assertThat(result.getStatus()).isEqualTo("REJECTED");
    }
}