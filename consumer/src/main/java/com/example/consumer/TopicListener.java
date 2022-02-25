package com.example.consumer;

import com.example.consumer.models.Customer;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

@Component
@EnableKafka
public class TopicListener {

    @KafkaListener(topics = "toInactiveCustomers",groupId = "example")
    void listener(Customer data){
        try {
            System.out.printf("| Received | %s %n", data.getFirstName());

        }catch (MessageConversionException m){
            System.out.println("Couldn't convert message");
        }
    }

}
