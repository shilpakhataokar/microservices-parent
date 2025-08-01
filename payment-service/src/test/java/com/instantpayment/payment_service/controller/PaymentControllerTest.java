package com.instantpayment.payment_service.controller;

import com.instantpayment.payment_service.dto.FraudCheckResult;
import com.instantpayment.payment_service.dto.PaymentRequest;
import com.instantpayment.payment_service.service.PaymentProcessingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentProcessingService paymentService;

    @Test
    void shouldReturnFraudCheckResult() throws Exception {
        FraudCheckResult result = new FraudCheckResult();
        result.setTransactionId("tx123");
        result.setStatus("APPROVED");
        result.setMessage("OK");

        Mockito.when(paymentService.processPayment(any(PaymentRequest.class)))
                .thenReturn(result);

        String json = """
                    {
                      "transactionId": "tx123",
                      "payerName": "John",
                      "payerBank": "BankA",
                      "payerCountryCode": "US",
                      "payerAccountNumber": "123",
                      "payeeName": "Jane",
                      "payeeBank": "BankB",
                      "payeeCountryCode": "CA",
                      "payeeAccountNumber": "456",
                      "paymentInstruction": "Test",
                      "paymentDate": "2025-07-29",
                      "paymentTimestamp": "2025-07-29T10:00:00Z",
                      "amount": 100.0,
                      "currency": "USD"
                    }
                """;

        mockMvc.perform(post("/api/payments/processPayment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").value("tx123"))
                .andExpect(jsonPath("$.status").value("APPROVED"))
                .andExpect(jsonPath("$.message").value("OK"));
    }
}