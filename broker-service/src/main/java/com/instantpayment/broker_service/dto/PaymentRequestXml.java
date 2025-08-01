package com.instantpayment.broker_service.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.math.BigDecimal;


@Data
@XmlRootElement(name = "PaymentRequestXML")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentRequestXml {
    @XmlElement
    private String transactionId;
    @XmlElement
    private String payerName;
    @XmlElement
    private String payerBank;
    @XmlElement
    private String payerCountryCode;
    @XmlElement
    private String payerAccount;
    @XmlElement
    private String payeeName;
    @XmlElement
    private String payeeBank;
    @XmlElement
    private String payeeCountryCode;
    @XmlElement
    private String payeeAccount;
    @XmlElement
    private String paymentInstruction;
    @XmlElement
    private String executionDate;
    @XmlElement
    private String creationTimestamp;
    @XmlElement
    private BigDecimal amount;
    @XmlElement
    private String currency;

    public PaymentRequestXml() {
    }

    public PaymentRequestXml(String transactionId, String payerName, String payerBank,
                             String payerCountryCode, String payerAccount, String payeeName,
                             String payeeBank, String payeeCountryCode, String payeeAccount,
                             String paymentInstruction, String executionDate,
                             String creationTimestamp, BigDecimal amount, String currency) {
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerBank() {
        return payerBank;
    }

    public void setPayerBank(String payerBank) {
        this.payerBank = payerBank;
    }

    public String getPayerCountryCode() {
        return payerCountryCode;
    }

    public void setPayerCountryCode(String payerCountryCode) {
        this.payerCountryCode = payerCountryCode;
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeBank() {
        return payeeBank;
    }

    public void setPayeeBank(String payeeBank) {
        this.payeeBank = payeeBank;
    }

    public String getPayeeCountryCode() {
        return payeeCountryCode;
    }

    public void setPayeeCountryCode(String payeeCountryCode) {
        this.payeeCountryCode = payeeCountryCode;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    public String getPaymentInstruction() {
        return paymentInstruction;
    }

    public void setPaymentInstruction(String paymentInstruction) {
        this.paymentInstruction = paymentInstruction;
    }

    public String getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
