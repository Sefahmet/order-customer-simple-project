package com.example.tesodevchallenge.service.impl;

import com.example.tesodevchallenge.exception.InvalidRequestException;
import com.example.tesodevchallenge.exception.NotFoundException;
import com.example.tesodevchallenge.model.entity.Customer;
import com.example.tesodevchallenge.repository.CustomerRepository;
import com.example.tesodevchallenge.service.CustomerService;
import com.example.tesodevchallenge.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final OrderService orderService;

    @Override
    public UUID createCustomer(Customer customer) {
        try{
            //Generate UUID
            UUID uuid = UUID.randomUUID();
            //Date Formate
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            Date date = new Date();
            customer.setId(uuid);
            customer.setCreatedAt(date);
            customer.setUpdatedAt(date);
            // Save Via JPA repository to DB
            customerRepository.save(customer);
            return uuid;

        }catch (Exception e){
            throw new InvalidRequestException("{validation.invalid.customer.create}");
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        if(validate(customer.getId())) {
            // Find Updatable customer
            Optional<Customer> recordedCustomer = customerRepository.findById(customer.getId());
            // Date Format
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            //Set changing
            customer.setUpdatedAt(date);
            customer.setCreatedAt(recordedCustomer.get().getCreatedAt());
            // Save Via JPA repository to DB
            customerRepository.save(customer);
            return true;
        }else{
            throw new InvalidRequestException("{validation.invalid.customer.update}");
        }

    }

    @Override
    public boolean deleteCustomer(UUID id) {
        try{
            // if customer exist
            Customer customer = getCustomer(id);
            //delete via JPA repository
            customerRepository.delete(customer);
            return true;
        }catch (Exception e ){
            return false;
        }
    }

    @Override
    public List<Customer> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    @Override
    public Customer getCustomer(UUID id) {
        //return customer ->if customer exist
        // throw Exception -> else
        Optional<Customer> byId = customerRepository.findById(id);
        return byId.orElseThrow(() -> new NotFoundException("Customer (id : '" + id + "')") );

    }

    public boolean validate(UUID id){
        try{
            Customer customer = customerRepository.findById(id).get();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
