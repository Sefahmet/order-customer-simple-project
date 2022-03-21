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
            UUID uuid = UUID.randomUUID();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            customer.setId(uuid);
            customer.setCreatedAt(date);
            customer.setUpdatedAt(date);
            customerRepository.save(customer);
            return uuid;

        }catch (Exception e){
            throw new InvalidRequestException("Request Denied");
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {

        try{
            Customer recordedCustomer = getCustomer(customer.getId());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            customer.setUpdatedAt(date);
            customer.setCreatedAt(recordedCustomer.getCreatedAt());
            customerRepository.save(customer);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteCustomer(UUID id) {
        try{
            Customer customer = getCustomer(id);
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

        Optional<Customer> byId = customerRepository.findById(id);
        return byId.orElseThrow(() -> new NotFoundException("Customer"));

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
