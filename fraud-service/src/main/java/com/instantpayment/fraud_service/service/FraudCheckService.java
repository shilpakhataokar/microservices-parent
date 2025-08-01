package com.instantpayment.fraud_service.service;

import com.instantpayment.fraud_service.dto.FraudCheckResultXml;
import com.instantpayment.fraud_service.dto.PaymentRequest;
import com.instantpayment.fraud_service.util.FraudDetectionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Set;

@Service
public class FraudCheckService {

    private static final Set<String> BLACKLISTED_NAMES = Collections.unmodifiableSet(
            Set.of("Mark Imaginary", "Govind Real", "Shakil Maybe", "Chang Imagine"));
    private static final Set<String> BLACKLISTED_COUNTRIES = Collections.unmodifiableSet(
            Set.of("CUB", "IRQ", "IRN", "PRK", "SDN", "SYR"));
    private static final Set<String> BLACKLISTED_BANKS = Collections.unmodifiableSet(
            Set.of("BANK OF KUNLUN", "KARAMAY CITY COMMERCIAL BANK"));
    private static final Set<String> BLACKLISTED_INSTRUCTIONS = Collections.unmodifiableSet(
            Set.of("Artillery Procurement", "Lethal Chemicals payment"));
    @Autowired
    private FraudDetectionProcessor fraudDetectionProcessor;

    public FraudCheckResultXml checkFraud(PaymentRequest paymentRequest) {
        if (isBlacklistedName(paymentRequest.getPayerName()) || isBlacklistedName(paymentRequest.getPayeeName())) {
            return reject(paymentRequest, "Suspicious Payer Name or Payee Name");
        }
        if (isBlacklistedCountry(paymentRequest.getPayerCountryCode()) || isBlacklistedCountry(paymentRequest.getPayeeCountryCode())) {
            return reject(paymentRequest, "Suspicious Country Code");
        }
        if (isBlacklistedBank(paymentRequest.getPayerBank()) || isBlacklistedBank(paymentRequest.getPayeeBank())) {
            return reject(paymentRequest, "Suspicious Payer/Payee Bank");
        }
        if (isBlacklistedInstruction(paymentRequest.getPaymentInstruction())) {
            return reject(paymentRequest, "Suspicious payment instruction");
        }
        return new FraudCheckResultXml(paymentRequest.getTransactionId(), "APPROVED", "Nothing found, all okay");
    }

    private boolean isBlacklistedName(String name) {
        return StringUtils.hasText(name) && BLACKLISTED_NAMES.contains(name);
    }

    private boolean isBlacklistedCountry(String code) {
        return StringUtils.hasText(code) && BLACKLISTED_COUNTRIES.contains(code);
    }

    private boolean isBlacklistedBank(String bank) {
        return StringUtils.hasText(bank) && BLACKLISTED_BANKS.contains(bank.toUpperCase());
    }

    private boolean isBlacklistedInstruction(String instruction) {
        return StringUtils.hasText(instruction) && BLACKLISTED_INSTRUCTIONS.contains(instruction);
    }

    private FraudCheckResultXml reject(PaymentRequest req, String message) {
        return new FraudCheckResultXml(req.getTransactionId(), "REJECTED", message);
    }
}
