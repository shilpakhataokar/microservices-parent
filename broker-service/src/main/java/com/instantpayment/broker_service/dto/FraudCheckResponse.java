package com.instantpayment.broker_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FraudCheckResponse {
    private String status;
    private String transactionId;
    private String message;
}
