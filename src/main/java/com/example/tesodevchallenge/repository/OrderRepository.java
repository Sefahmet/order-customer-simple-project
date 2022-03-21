package com.example.tesodevchallenge.repository;

import com.example.tesodevchallenge.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> getOrdersByCustomerId(@Param("customer_id") UUID uuid);

}
