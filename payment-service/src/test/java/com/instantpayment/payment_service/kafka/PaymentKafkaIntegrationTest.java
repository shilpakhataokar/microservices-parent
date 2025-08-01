package com.instantpayment.payment_service.kafka;


import com.instantpayment.payment_service.dto.PaymentRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "payments.requests" })
@Import(KafkaTestConfig.class)
class PaymentKafkaIntegrationTest {

    @Autowired
    private KafkaTemplate<String, PaymentRequest> kafkaTemplate;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Test
    void shouldSendAndReceivePaymentRequest() {
        PaymentRequest req = new PaymentRequest();
        req.setPayerCountryCode("US");

        kafkaTemplate.send("payments.requests", req);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafka);
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        DefaultKafkaConsumerFactory<String, PaymentRequest> cf =
                new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), new JsonDeserializer<>(PaymentRequest.class, false));
        var consumer = cf.createConsumer();
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, "payments.requests");

        ConsumerRecord<String, PaymentRequest> record = KafkaTestUtils.getSingleRecord(consumer, "payments.requests");
        assertThat(record.value().getPayerCountryCode()).isEqualTo("US");
        consumer.close();
    }
}