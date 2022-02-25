package com.example.consumer;

import com.example.consumer.models.Customer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TopicListener {

    @KafkaListener(topics = "toInactiveCustomers", groupId = "snafuOne")
    void listener(Customer data){
        System.out.printf("| Received | %s %n", data.getFirstName());
    }

}
