package com.example.emailnotificationservice.handler;

import com.example.emailnotificationservice.error.NotRetryableException;
import com.example.emailnotificationservice.error.RetryableException;
import com.example.emailnotificationservice.handler.model.ProductCreateEventModel;
import org.apache.kafka.common.errors.RetriableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductCreateEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private RestTemplate restTemplate;

    public ProductCreateEventHandler(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @KafkaListener(topics = "product-create-update-event-topic")
    public void handle(ProductCreateEventModel productCreateEventModel){
        LOGGER.info("*** New Event received ***");


        String url = "http://localhost:8082/response/200";
        try{
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            LOGGER.info("Received Response From another Service !!!" + response.getBody());
        } catch(ResourceAccessException e){
            LOGGER.error(e.getMessage());
            throw new RetryableException("Retryable Exception occurred !!!");
        } catch (HttpServerErrorException exception){
            LOGGER.error(exception.getMessage());
            throw new NotRetryableException("Not Retryable Exception occurred !!!");
        } catch (Exception exception){
            LOGGER.error(exception.getMessage());
            throw new NotRetryableException("Not Retryable Exception occurred !!!");
        }

        LOGGER.info("New Event received : Product - "+ productCreateEventModel.getTitle());
        LOGGER.info("New Event received : Product - "+ productCreateEventModel.getQuantity());
        LOGGER.info("New Event received : Product - "+ productCreateEventModel.getPrice());
    }
}
