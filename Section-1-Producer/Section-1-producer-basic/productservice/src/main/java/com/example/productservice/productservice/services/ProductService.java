package com.example.productservice.productservice.services;

import com.example.productservice.productservice.events.ProductCreateEvent;
import com.example.productservice.productservice.model.CreatedProductModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductService {

    @Autowired
    KafkaTemplate<String, ProductCreateEvent> kafkaTemplate;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public String createProduct(CreatedProductModel product) throws Exception{
        ProductCreateEvent productCreateEvent = new ProductCreateEvent(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getQuantity()
        );

        // asynchronous operation
        CompletableFuture<SendResult<String, ProductCreateEvent>> future =  kafkaTemplate.send("product-created-events-topic", product.getId().toString(), productCreateEvent);
        future.whenComplete((result, exception) -> {
           if(exception != null){
               LOGGER.error("Failed to sent message for " + productCreateEvent.getTitle() + exception.getMessage());
           } else {
               LOGGER.info("Successfully sent message for " + productCreateEvent.getTitle() + result.getRecordMetadata().toString());
           }
        });
        // end asynchronous operation

        // Synchronous operation
//        LOGGER.info("***** Before Publishing event ****" + product.getTitle());
//        SendResult<String, ProductCreateEvent> result =  kafkaTemplate.send("product-created-events-topic", product.getId().toString(), productCreateEvent).get();
//        LOGGER.info("***** After Publishing event, result - " + result.getRecordMetadata().toString());
        // end Synchronous operation

        LOGGER.info("***** Returning Product ****" + product.getTitle());
        return "Product Created - " + product.getTitle();
    }
}
