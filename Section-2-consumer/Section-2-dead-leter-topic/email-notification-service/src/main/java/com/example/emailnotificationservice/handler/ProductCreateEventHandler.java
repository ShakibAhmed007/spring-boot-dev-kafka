package com.example.emailnotificationservice.handler;

import com.example.emailnotificationservice.handler.model.ProductCreateEventModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = "product-create-update-event-topic")
    public void handle(ProductCreateEventModel productCreateEventModel){
        LOGGER.info("New Event received : Product - "+ productCreateEventModel.getId());
        LOGGER.info("New Event received : Product - "+ productCreateEventModel.getTitle());
        LOGGER.info("New Event received : Product - "+ productCreateEventModel.getQuantity());
        LOGGER.info("New Event received : Product - "+ productCreateEventModel.getPrice());
    }
}
