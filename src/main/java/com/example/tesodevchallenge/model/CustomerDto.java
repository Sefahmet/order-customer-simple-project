package com.example.tesodevchallenge.model;

import com.example.tesodevchallenge.model.entity.Address;
import com.example.tesodevchallenge.model.entity.Order;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CustomerDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<Order> oldOrders;
    private List<Address> addresses;

}
