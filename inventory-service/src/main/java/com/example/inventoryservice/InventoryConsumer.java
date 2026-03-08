package com.example.inventoryservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryConsumer {

    private static final Logger log = LoggerFactory.getLogger(InventoryConsumer.class);

    @KafkaListener(topics = "order-topic", groupId = "inventory-group")
    public void consume(String order) {
        log.info("Inventory received order: {}", order);
        log.info("Stock updated");
    }
}
