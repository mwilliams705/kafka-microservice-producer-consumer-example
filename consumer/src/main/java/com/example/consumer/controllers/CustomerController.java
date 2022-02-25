package com.example.consumer.controllers;

import com.example.consumer.models.Customer;
import com.example.consumer.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@AllArgsConstructor
public class CustomerController {
    
    private final CustomerRepository repository;
    
    @GetMapping
    public List<Customer> returnAllCustomers(){
        return repository.findAll();
    }
    
}
