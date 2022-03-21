package com.example.tesodevchallenge.model.mapper;

import com.example.tesodevchallenge.model.CustomerDto;
import com.example.tesodevchallenge.model.entity.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    CustomerDto toDto(Customer customer);
    Customer toEntity(CustomerDto customerDto);
}
