package com.instantpayment.payment_service.kafka;

import com.instantpayment.payment_service.dto.PaymentRequest;
import com.instantpayment.payment_service.service.PaymentProcessingService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PaymentRequestConsumerTest {

    @Test
    void shouldCallProcessPayment() {
        PaymentProcessingService service = mock(PaymentProcessingService.class);
        PaymentRequestConsumer consumer = new PaymentRequestConsumer(service);

        PaymentRequest req = new PaymentRequest();
        consumer.listen(req);

        verify(service).processPayment(req);
    }
}