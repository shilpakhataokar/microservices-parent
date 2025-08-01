package com.instantpayment.broker_service.util;

import com.instantpayment.broker_service.dto.PaymentRequestXml;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.StringWriter;
import java.math.BigDecimal;

public class PaymentRequestConverter {

    public static String convertToXml(PaymentRequestXml request) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PaymentRequestXml.class); // Create a JAXBContext instance for your class.
        Marshaller marshaller = context.createMarshaller(); // Create a Marshaller instance to convert Java objects to XML.

        // Configure the marshaller (optional)
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // This property enables pretty-print XML output.

        StringWriter writer = new StringWriter();
        marshaller.marshal(request, writer); // Marshal the PaymentRequest object to the StringWriter.

        return writer.toString();
    }

    public static void main(String[] args) throws JAXBException {
        // Create a sample PaymentRequest object
        PaymentRequestXml request = new PaymentRequestXml(
                "TRN12345", "John Doe", "Bank A", "US", "123456789",
                "Jane Doe", "Bank B", "CA", "987654321",
                "Invoice payment", "2025-07-29", "2025-07-29T10:00:00Z",
                new BigDecimal("1000.50"), "USD");

        // Set other fields...
        System.out.println(request);
        String xmlOutput = convertToXml(request); // Convert the Java object to an XML string.
        System.out.println(xmlOutput);
    }

}