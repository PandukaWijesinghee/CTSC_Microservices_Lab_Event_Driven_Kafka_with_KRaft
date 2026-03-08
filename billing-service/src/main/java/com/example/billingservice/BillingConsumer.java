package com.example.billingservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BillingConsumer {

    private static final Logger log = LoggerFactory.getLogger(BillingConsumer.class);

    @KafkaListener(topics = "order-topic", groupId = "billing-group")
    public void consume(String order) {
        log.info("Billing received order: {}", order);
        log.info("Invoice generated");
    }
}
