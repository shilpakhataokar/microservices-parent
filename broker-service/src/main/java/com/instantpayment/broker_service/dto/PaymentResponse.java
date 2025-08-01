package com.instantpayment.broker_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private String status;
    private String transactionId;
    private String message;
    private String errorCode;
    private double amount;
    private String currency;

}
