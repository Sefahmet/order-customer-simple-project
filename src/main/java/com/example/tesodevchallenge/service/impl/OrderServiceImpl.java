package com.example.tesodevchallenge.service.impl;

import com.example.tesodevchallenge.exception.NotFoundException;
import com.example.tesodevchallenge.model.entity.Address;
import com.example.tesodevchallenge.model.entity.Customer;
import com.example.tesodevchallenge.model.entity.Order;
import com.example.tesodevchallenge.repository.AddressRepository;
import com.example.tesodevchallenge.repository.CustomerRepository;
import com.example.tesodevchallenge.repository.OrderRepository;
import com.example.tesodevchallenge.service.AddressService;
import com.example.tesodevchallenge.service.CustomerService;
import com.example.tesodevchallenge.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final AddressService addressService;

    @Override
    public UUID createOrder(Order order, UUID address_id, UUID customer_id) {

        UUID uuid = UUID.randomUUID();
        order.setId(uuid);
        Customer customer = customerRepository.getById(customer_id);
        Address address = addressService.getAdrress(address_id);
        order.setAddress(address);
        order.setCustomer(customer);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        order.setCreatedAt(date);
        order.setUpdatedAt(date);
        orderRepository.save(order);
        return uuid;
    }

    @Override
    public boolean updateOrder(Order order) {

        Optional<Order> recordedOrder = (orderRepository.findById(order.getId()));
        if(recordedOrder.isPresent()){
            order.setId(recordedOrder.get().getId());
            order.setAddress(recordedOrder.get().getAddress());
            order.setCustomer(recordedOrder.get().getCustomer());
            order.setId(recordedOrder.get().getId());
            order.setCreatedAt(recordedOrder.get().getCreatedAt());

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            order.setUpdatedAt(date);

            orderRepository.save(order);
            return true;

        }

        return false;

    }

    @Override
    public boolean deleteOrder(UUID id) {
        changeStatus(id,"deleted");
        return true;
    }

    @Override
    public List<Order> getAllOrder() {

        return orderRepository.findAllByStatusIsNot("deleted");
    }

    @Override
    public List<Order> getOrdersByCustomerId(UUID id) {

        return orderRepository.findAllByCustomer(id);
    }

    @Override
    public Order getOrder(UUID id) {
        Optional<Order> byId = orderRepository.findById(id);
        return byId.orElseThrow(()-> new NotFoundException("Order"));
    }


    @Override
    public boolean changeStatus(UUID id, String status) {

        Optional<Order> recordedOrder = orderRepository.findById(id);
        if(recordedOrder.isPresent()){
            Order order = recordedOrder.get();
            order.setStatus(status);
            orderRepository.save(order);
            return true;
        }
        return false;
    }
}
