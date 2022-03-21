package com.example.tesodevchallenge.model;

import com.example.tesodevchallenge.model.entity.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.mapstruct.Mapper;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {



    private UUID id;
    private Integer quantity;
    private float price;
    private String status;
    private Address address;
    private Date createdAt;
    private Date updatedAt;
    private Address addresses;


}
