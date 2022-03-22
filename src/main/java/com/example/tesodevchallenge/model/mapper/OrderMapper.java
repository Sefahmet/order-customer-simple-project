package com.example.tesodevchallenge.model.mapper;

import com.example.tesodevchallenge.model.CustomerDto;
import com.example.tesodevchallenge.model.OrderDto;
import com.example.tesodevchallenge.model.entity.Address;
import com.example.tesodevchallenge.model.entity.Customer;
import com.example.tesodevchallenge.model.entity.Order;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderMapper {
    OrderDto toDto(Order order);
    Order toEntity(OrderDto orderDto);
    public default List<OrderDto> toDtos(List<Order> orders){
        List<OrderDto> response= new ArrayList<>();
        for (Order o:orders) {
            OrderDto orderDto = toDto(o);
            Address address = orderDto.getAddress();
            address.setCustomer(null);
            orderDto.setAddress(address);
            response.add(orderDto);
        }
        return response;
    }

}
