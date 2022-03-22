package com.example.tesodevchallenge.model;

import com.example.tesodevchallenge.model.entity.Address;
import com.example.tesodevchallenge.model.entity.Customer;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class OrderDto {



    private UUID id;
    private Integer quantity;
    private Integer price;
    private String status;
    private Address address;
    private Customer customer;
    private Date createdAt;
    private Date updatedAt;


}
