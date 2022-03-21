package com.example.tesodevchallenge.model.mapper;

import com.example.tesodevchallenge.model.CustomerDto;
import com.example.tesodevchallenge.model.ProductDto;
import com.example.tesodevchallenge.model.entity.Customer;
import com.example.tesodevchallenge.model.entity.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
}
