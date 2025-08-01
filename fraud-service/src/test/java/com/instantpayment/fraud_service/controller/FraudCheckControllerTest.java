package com.instantpayment.fraud_service.controller;

import com.instantpayment.fraud_service.dto.FraudCheckResultXml;
import com.instantpayment.fraud_service.dto.PaymentRequest;
import com.instantpayment.fraud_service.service.FraudCheckService;
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

@WebMvcTest(FraudCheckController.class)
class FraudCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FraudCheckService fraudCheckService;

    @Test
    void shouldReturnFraudCheckResultXml() throws Exception {
        FraudCheckResultXml result = new FraudCheckResultXml("tx1", "APPROVED", "all okay");
        Mockito.when(fraudCheckService.checkFraud(any(PaymentRequest.class))).thenReturn(result);

        String json = """
                    {
                      "transactionId": "tx1",
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

        mockMvc.perform(post("/api/fraud/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("tx1"))
                .andExpect(jsonPath("$.status").value("APPROVED"))
                .andExpect(jsonPath("$.message").value("all okay"));
    }
}