package com.instantpayment.broker_service.controller;

import com.instantpayment.broker_service.dto.FraudCheckResult;
import com.instantpayment.broker_service.dto.FraudCheckResultXml;
import com.instantpayment.broker_service.dto.PaymentRequest;
import com.instantpayment.broker_service.dto.PaymentRequestXml;
import com.instantpayment.broker_service.service.BrokerService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/broker")
public class BrokerController {

    private final RestTemplate restTemplate;
    private final BrokerService brokerService;

    @Value("${fcs.fraud-check.url}")
    private String fcsUrl;

    public BrokerController(RestTemplate restTemplate, BrokerService brokerService) {
        this.restTemplate = restTemplate;
        this.brokerService = brokerService;
    }

    @PostMapping("/fraud-check")
    @ResponseStatus(HttpStatus.CREATED)
    public FraudCheckResult performFraudCheck(@RequestBody PaymentRequest paymentRequest) throws Exception {
        PaymentRequestXml xmlReq = new PaymentRequestXml();
        BeanUtils.copyProperties(paymentRequest, xmlReq);

        String xmlPayload;
        try (StringWriter sw = new StringWriter()) {
            JAXBContext context = JAXBContext.newInstance(PaymentRequestXml.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(xmlReq, sw);
            xmlPayload = sw.toString();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<String> entity = new HttpEntity<>(xmlPayload, headers);

        String responseXml;
        try {
            ResponseEntity<String> xmlResponse = restTemplate.exchange(
                    fcsUrl, HttpMethod.POST, entity, String.class);
            responseXml = xmlResponse.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Error calling FCS service", e);
        }

        FraudCheckResultXml resultXml;
        try (StringReader reader = new StringReader(responseXml)) {
            JAXBContext resultContext = JAXBContext.newInstance(FraudCheckResultXml.class);
            Unmarshaller unmarshaller = resultContext.createUnmarshaller();
            resultXml = (FraudCheckResultXml) unmarshaller.unmarshal(reader);
        }

        FraudCheckResult result = new FraudCheckResult();
        BeanUtils.copyProperties(resultXml, result);
        return result;
    }

    @GetMapping("/fraudRequest")
    public PaymentRequestXml payment() {
        return new PaymentRequestXml(
                "TRN12345", "John Doe", "Bank A", "US", "123456789",
                "Jane Doe", "Bank B", "CA", "987654321",
                "Invoice payment", "2025-07-29", "2025-07-29T10:00:00Z",
                new BigDecimal("1000.50"), "USD");
    }
}