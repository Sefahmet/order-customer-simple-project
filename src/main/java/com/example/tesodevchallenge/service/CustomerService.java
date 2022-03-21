package com.example.tesodevchallenge.service;

import com.example.tesodevchallenge.exception.NotFoundException;
import com.example.tesodevchallenge.model.entity.Customer;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


public interface CustomerService {

    UUID createCustomer(@Valid @RequestBody Customer customer);
    boolean updateCustomer(@Valid @RequestBody Customer customer);
    boolean deleteCustomer(UUID id);
    List<Customer> getAllCustomer();
    Customer getCustomer(UUID id);
    boolean validate(UUID id);
/*
    boolean Validate(UUID id);
*/

}
