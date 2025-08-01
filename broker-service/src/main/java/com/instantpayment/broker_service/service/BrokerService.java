package com.instantpayment.broker_service.service;

import com.instantpayment.broker_service.dto.FraudCheckResponse;
import com.instantpayment.broker_service.dto.PaymentRequestXml;
import com.instantpayment.broker_service.util.PaymentRequestConverter;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BrokerService {

    /* 1.Converts JSON to XML
       2. Sends XML to FCS over JMS (queue:fraud.queue)
       3. Receives XML response from FCS (queue:fraud.response.queue)
       4. Converts XML to JSON
       5. Sends final response to PPS (via in-memory or callback JMS/REST) */
    @Autowired
    private RestTemplate restTemplate;

    public FraudCheckResponse performFraudCheck(PaymentRequestXml paymentRequest) throws JAXBException {

        //Json to XML converter
        //call to FCS service
        //convert XML to JSON Converter
        //PaymentRequestXml paymentRequestXml = JsonToXmlConverter.convertJsonToXml(paymentRequest);
        PaymentRequestConverter.convertToXml(paymentRequest);
        // make a rest Call to Broker Service
        String fraudServiceUrl = "http://localhost:8081/fraud-check/request";
        return restTemplate.postForObject(fraudServiceUrl, paymentRequest, FraudCheckResponse.class);

    }

}
