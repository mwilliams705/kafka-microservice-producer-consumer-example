package com.example.producer.controllers;

import com.example.producer.models.Customer;
import com.example.producer.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerRepository repository;
    private final KafkaTemplate<String, Customer> kafkaCustomerTemplate;

    @GetMapping
    public List<Customer> returnAllCustomers(){
        return repository.findAll();
    }
    
    @PostMapping
    public Customer addOne(@RequestBody Customer customer){
        return repository.save(customer);
    }
    
    @PostMapping("/deactivate/{idToDeactivate}")
    public String addToInactive(@PathVariable Long idToDeactivate){
        
//        TODO: Figure out LazyLoad Error causing kafka to throw exception 
        final Customer c = repository.getById(idToDeactivate);
        if (c.isActive()){
            c.setActive(false);
            repository.save(c);
            kafkaCustomerTemplate.send("toInactiveCustomers", c);
            return c.toString() + " sent!";
        }
        return "Customer already inactive";
        
    }
}
