package com.example.producer.controllers;

import com.example.producer.models.Customer;
import com.example.producer.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
         Optional<Customer> o = repository.findById(idToDeactivate);
         if (o.isPresent()){
             System.out.println(o.get().getFirstName());
             Customer c = o.get();

             if (c.isActive()){
                 c.setActive(false);
                 repository.save(c);

                 kafkaCustomerTemplate.send("toInactiveCustomers", new Customer(c.getCustomerId(),c.getFirstName(),c.getLastName(),c.isActive()));
                 return c.getCustomerId() + " sent!";
             }
             return "Customer already inactive";
         }
        return "No Customer Found";

        
    }
}
