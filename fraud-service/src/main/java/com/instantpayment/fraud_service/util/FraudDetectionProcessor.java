package com.instantpayment.fraud_service.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.instantpayment.fraud_service.dto.FraudCheckResultXml;
import com.instantpayment.fraud_service.dto.PaymentRequest;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.util.Set;

@Component
public class FraudDetectionProcessor {
    static final Set<String> BLACKLISTED_NAMES = Set.of("Mark Imaginary", "Govind Real", "Shakil Maybe", "Chang Imagine");
    static final Set<String> BLACKLISTED_COUNTRIES = Set.of("CUB", "IRQ", "IRN", "PRK", "SDN", "SYR");
    static final Set<String> BLACKLISTED_BANKS = Set.of("BANK OF KUNLUN", "KARAMAY CITY COMMERCIAL BANK");
    static final Set<String> BLACKLISTED_INSTRUCTIONS = Set.of("Artillery Procurement", "Lethal Chemicals payment");


    public FraudCheckResultXml performFraudCheck(PaymentRequest request) {
        XmlMapper xmlMapper = new XmlMapper();
        PaymentRequest paymentRequest;

        try {
            paymentRequest = xmlMapper.readValue((XMLStreamReader) request, PaymentRequest.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean isFraud = isBlacklisted(paymentRequest);

        FraudCheckResultXml response = new FraudCheckResultXml();
        response.setTransactionId(request.getTransactionId());
        response.setStatus(isFraud ? "REJECTED" : "APPROVED");
        response.setMessage(isFraud ? "Suspicious payment" : "Nothing found, all okay");

        return response;
    }


    private boolean isBlacklisted(PaymentRequest request) {
        return BLACKLISTED_NAMES.contains(request.getPayerName()) ||
                BLACKLISTED_NAMES.contains(request.getPayeeName()) ||
                BLACKLISTED_COUNTRIES.contains(request.getPayerCountryCode()) ||
                BLACKLISTED_COUNTRIES.contains(request.getPayeeCountryCode()) ||
                BLACKLISTED_BANKS.contains(request.getPayerBank()) ||
                BLACKLISTED_BANKS.contains(request.getPayeeBank()) ||
                (request.getPaymentInstruction() != null && BLACKLISTED_INSTRUCTIONS.contains(request.getPaymentInstruction()));
    }


}
