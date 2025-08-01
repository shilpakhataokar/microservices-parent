package com.instantpayment.payment_service.util;

import com.instantpayment.payment_service.dto.PaymentRequest;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.Locale;
import java.util.Set;

@Component
public class PaymentValidator {

    public static final String VALID_PAYMENT = "valid";
    private static final Set<String> ISO_COUNTRIES = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3);

    public String validate(PaymentRequest paymentRequest) {
        if (paymentRequest == null) {
            return "PaymentRequest is null";
        }
        if(paymentRequest.getAmount() == null || paymentRequest.getAmount().doubleValue() <0) {
            return "Amount cannot be null or negative";
        }
        String payerCountry = paymentRequest.getPayerCountryCode();
        if (payerCountry == null) {
            return "Payer country code is null";
        }
        if (!ISO_COUNTRIES.contains(payerCountry)) {
            return "Invalid ISO country code for Payer: " + payerCountry;
        }
        String payeeCountry = paymentRequest.getPayeeCountryCode();
        if (payeeCountry == null) {
            return "Payee country code is null";
        }
        if (!ISO_COUNTRIES.contains(payeeCountry)) {
            return "Invalid ISO country code for Payee: " + payeeCountry;
        }
        String currency = paymentRequest.getCurrency();
        if (currency == null) {
            return "Currency is null";
        }
        try {
            Currency.getInstance(currency);
        } catch (IllegalArgumentException ex) {
            return "Invalid ISO currency code: " + currency;
        }
        return VALID_PAYMENT;

    }


}
