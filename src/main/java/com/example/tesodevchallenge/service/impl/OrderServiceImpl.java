package com.example.tesodevchallenge.service.impl;

import com.example.tesodevchallenge.exception.NotFoundException;
import com.example.tesodevchallenge.model.Address;
import com.example.tesodevchallenge.model.entity.Order;
import com.example.tesodevchallenge.repository.OrderRepository;
import com.example.tesodevchallenge.service.AddressService;
import com.example.tesodevchallenge.service.OrderService;
import jdk.jfr.events.ExceptionStatisticsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final AddressService addressService;

    @Override
    public UUID createOrder(Order order) {

        UUID uuid = UUID.randomUUID();
        order.setId(uuid);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        order.setCreatedAt(date);
        order.setUpdatedAt(date);
        orderRepository.save(order);
        return uuid;
    }

    @Override
    public boolean updateOrder(Order order) {
        try{
            Optional<Order> recordedOrder = (orderRepository.findById(order.getId()));
            System.out.println(recordedOrder.get());
            order.setId(recordedOrder.get().getId());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            order.setUpdatedAt(date);
            order.setCreatedAt(recordedOrder.get().getCreatedAt());
            orderRepository.save(order);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteOrder(UUID id) {
        orderRepository.delete(getOrder(id));
        return true;
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByCustomerId(UUID id) {

        return orderRepository.getOrdersByCustomerId(id);
    }

    @Override
    public Order getOrder(UUID id) {
        Optional<Order> byId = orderRepository.findById(id);
        if(byId.isPresent()){
            byId.get().getAddress().setCustomer(null);
            return byId.get();
        }
        throw new NotFoundException("Order");
    }


    @Override
    public boolean changeStatus(UUID id, String status) {
        try{
            Order recordedOrder = getOrder(id);
            System.out.println(recordedOrder);
            recordedOrder.setStatus(status);
            orderRepository.save(recordedOrder);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
