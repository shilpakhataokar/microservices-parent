package com.instantpayment.payment_service.kafka;


import com.instantpayment.payment_service.dto.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FraudCheckRequestProducerTest {

    @Test
    void shouldSendFraudCheckRequest() {
        KafkaTemplate<String, PaymentRequest> kafkaTemplate = mock(KafkaTemplate.class);
        FraudCheckRequestProducer producer = new FraudCheckRequestProducer(kafkaTemplate);

        PaymentRequest req = new PaymentRequest();
        req.setPayerCountryCode("US");
        producer.sendFraudCheckRequest(req);

        ArgumentCaptor<PaymentRequest> captor = ArgumentCaptor.forClass(PaymentRequest.class);
        verify(kafkaTemplate).send(eq("fraud.check.requests"), captor.capture());
        assertThat(captor.getValue().getPayerCountryCode()).isEqualTo("US");
    }
}