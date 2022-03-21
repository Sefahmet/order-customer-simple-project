package com.example.tesodevchallenge.service;

import com.example.tesodevchallenge.model.entity.Customer;
import com.example.tesodevchallenge.model.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    UUID createOrder(@Valid @RequestBody Order order);
    boolean updateOrder(@Valid @RequestBody Order order);
    boolean deleteOrder(UUID id);
    List<Order> getAllOrder();

    List<Order> getOrdersByCustomerId(UUID id);

    Order getOrder(UUID id);
/*    boolean validate(UUID id);*/
    boolean changeStatus(UUID id ,String status);

}
