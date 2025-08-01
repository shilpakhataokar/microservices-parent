package com.instantpayment.fraud_service.dto;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement(name = "PaymentResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    @XmlElement(name = "TransactionID")
    private String transactionId;

    @XmlElement(name = "Status")
    private String status; // APPROVED or REJECTED

    @XmlElement(name = "Message")
    private String message;

}