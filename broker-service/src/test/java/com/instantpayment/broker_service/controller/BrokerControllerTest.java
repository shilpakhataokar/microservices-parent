package com.instantpayment.broker_service.controller;

import com.instantpayment.broker_service.service.BrokerService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BrokerController.class)
class BrokerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private BrokerService brokerService;

    @Test
    void testPerformFraudCheck() throws Exception {
        String xmlResponse = """
                <FraudCheckResultXml>
                    <transactionId>TRN12345</transactionId>
                    <status>APPROVED</status>
                    <message>Nothing found, all okay</message>
                </FraudCheckResultXml>
                """;

        when(restTemplate.exchange(
                any(String.class),
                any(HttpMethod.class),
                any(HttpEntity.class),
                ArgumentMatchers.<Class<String>>any())
        ).thenReturn(new ResponseEntity<>(xmlResponse, HttpStatus.OK));

        String jsonRequest = """
                {
                  "transactionId": "TRN12345",
                  "payerName": "John Doe",
                  "payerBank": "Bank A",
                  "payerCountryCode": "US",
                  "payerAccountNumber": "123456789",
                  "payeeName": "Jane Doe",
                  "payeeBank": "Bank B",
                  "payeeCountryCode": "CA",
                  "payeeAccountNumber": "987654321",
                  "paymentInstruction": "Invoice payment",
                  "paymentDate": "2025-07-29",
                  "paymentTimestamp": "2025-07-29T10:00:00Z",
                  "amount": 1000.50,
                  "currency": "USD"
                }
                """;

        mockMvc.perform(post("/api/broker/fraud-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").value("TRN12345"))
                .andExpect(jsonPath("$.status").value("APPROVED"))
                .andExpect(jsonPath("$.message").value("Nothing found, all okay"));
    }


}