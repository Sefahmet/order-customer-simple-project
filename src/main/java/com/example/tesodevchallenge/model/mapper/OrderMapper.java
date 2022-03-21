package com.example.tesodevchallenge.model.mapper;

import com.example.tesodevchallenge.model.CustomerDto;
import com.example.tesodevchallenge.model.OrderDto;
import com.example.tesodevchallenge.model.entity.Customer;
import com.example.tesodevchallenge.model.entity.Order;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderMapper {

    OrderDto toDto(Order order);
    Order toEntity(OrderDto productDto);
    public default List<OrderDto> toDtos(List<Order> orders){
        List<OrderDto> response= new ArrayList<>();
        for (Order o:orders) {
            response.add(toDto(o));
        }
        return response;
    }
}
