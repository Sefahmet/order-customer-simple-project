package com.example.tesodevchallenge.controller;


import com.example.tesodevchallenge.exception.InvalidRequestException;
import com.example.tesodevchallenge.exception.NotFoundException;
import com.example.tesodevchallenge.model.CustomerDto;
import com.example.tesodevchallenge.model.entity.Customer;
import com.example.tesodevchallenge.model.mapper.CustomerMapper;
import com.example.tesodevchallenge.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    private static final CustomerMapper CUSTOMER_MAPPER = Mappers.getMapper(CustomerMapper.class);

    @GetMapping
    public String welcome(){return "Welcome to Customer Service";}


    @GetMapping(value = "/id")
    public ResponseEntity<Customer> getCustomer(@Valid @RequestParam UUID id){
        return new ResponseEntity(customerService.getCustomer(id),HttpStatus.OK);

    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return new ResponseEntity(customerService.getAllCustomer(), HttpStatus.OK);
    }



    @PostMapping(value = "/create")
    public ResponseEntity<UUID> createCustomer(@Valid @RequestBody Customer customer){
        return new ResponseEntity(customerService.createCustomer(customer),HttpStatus.OK);
    }


    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> deleteCustomer(@Valid @RequestParam UUID uuid) {
        boolean response = customerService.deleteCustomer(uuid);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
