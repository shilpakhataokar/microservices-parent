package com.instantpayment.payment_service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Data
@Getter
@Setter
public class PaymentRequest {

    private String transactionId;
    private String payerName;
    private String payerBank;
    private String payerCountryCode;
    private String payerAccount;
    private String payeeName;
    private String payeeBank;
    private String payeeCountryCode;
    private String payeeAccount;
    private String paymentInstruction;
    private String executionDate;
    private String creationTimestamp;
    private BigDecimal amount;
    private String currency;


    public PaymentRequest() {
    }

    public PaymentRequest(String transactionId, String payerName, String payerBank, String payerCountryCode, String payerAccount, String payeeName, String payeeBank, String payeeCountryCode, String payeeAccount, String paymentInstruction, String executionDate, String creationTimestamp, BigDecimal amount, String currency) {
        this.transactionId = transactionId;
        this.payerName = payerName;
        this.payerBank = payerBank;
        this.payerCountryCode = payerCountryCode;
        this.payerAccount = payerAccount;
        this.payeeName = payeeName;
        this.payeeBank = payeeBank;
        this.payeeCountryCode = payeeCountryCode;
        this.payeeAccount = payeeAccount;
        this.paymentInstruction = paymentInstruction;
        this.executionDate = executionDate;
        this.creationTimestamp = creationTimestamp;
        this.amount = amount;
        this.currency = currency;
    }
// Getters and Setters


}