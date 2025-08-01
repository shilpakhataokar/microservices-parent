package com.instantpayment.payment_service.util;

import com.instantpayment.payment_service.dto.PaymentRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentValidatorTest {

    private final PaymentValidator validator = new PaymentValidator();

    @Test
    void shouldReturnValidForCorrectRequest() {
        PaymentRequest req = new PaymentRequest();
        req.setTransactionId("tx1");
        req.setPayerName("John");
        req.setPayerBank("BankA");
        req.setPayerCountryCode("USA");
        req.setPayerAccount("123");
        req.setPayeeName("Jane");
        req.setPayeeBank("BankB");
        req.setPayeeCountryCode("IND");
        req.setPayeeAccount("456");
        req.setPaymentInstruction("Test");
        req.setExecutionDate("2025-07-29");
        req.setCreationTimestamp("2025-07-29T10:00:00Z");
        BigDecimal amount = BigDecimal.valueOf(100.0);
        req.setAmount(amount);
        req.setCurrency("USD");

        String result = validator.validate(req);
        assertThat(result).isEqualTo(PaymentValidator.VALID_PAYMENT);
    }

    @Test
    void shouldRejectMissingFields() {
        PaymentRequest req = new PaymentRequest();
        String result = validator.validate(req);
        assertThat(result).isNotEqualTo(PaymentValidator.VALID_PAYMENT);
    }

    @Test
    void shouldRejectNegativeAmount() {
        PaymentRequest req = new PaymentRequest();
        req.setTransactionId("tx1");
        req.setPayerName("John");
        req.setPayerBank("BankA");
        req.setPayerCountryCode("US");
        req.setPayerAccount("123");
        req.setPayeeName("Jane");
        req.setPayeeBank("BankB");
        req.setPayeeCountryCode("CA");
        req.setPayeeAccount("456");
        req.setPaymentInstruction("Test");
        req.setExecutionDate("2025-07-29");
        req.setCreationTimestamp("2025-07-29T10:00:00Z");
        BigDecimal amount = BigDecimal.valueOf(-100.0);
        req.setAmount(amount);
        req.setCurrency("USD");

        String result = validator.validate(req);
        assertThat(result).contains("Amount cannot be null or negative");
    }
}