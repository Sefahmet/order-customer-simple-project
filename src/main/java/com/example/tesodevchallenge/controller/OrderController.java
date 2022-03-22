package com.example.tesodevchallenge.controller;

import com.example.tesodevchallenge.model.OrderDto;
import com.example.tesodevchallenge.model.entity.Order;
import com.example.tesodevchallenge.model.mapper.CustomerMapper;
import com.example.tesodevchallenge.model.mapper.OrderMapper;
import com.example.tesodevchallenge.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private static final OrderMapper ORDER_MAPPER = Mappers.getMapper(OrderMapper.class);
    private final OrderService orderService;

    @GetMapping
    public String welcome(){return "Welcome to Order Service";}

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> getOrder(@Valid @PathVariable UUID id){
        return new ResponseEntity(orderService.getOrder(id), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<OrderDto>> allOrder(){
        List<Order> orders = orderService.getAllOrder();
        return new ResponseEntity(ORDER_MAPPER.toDtos(orders), HttpStatus.OK);
    }

    @GetMapping(value = "/customer/{cid}")
    public ResponseEntity<List<Order>> getOrders(@Valid @PathVariable UUID cid){
        return new ResponseEntity(orderService.getOrdersByCustomerId(cid),HttpStatus.OK);
    }

/*
    @GetMapping(value = "/{cid}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@RequestParam UUID cid){
        return new ResponseEntity(orderService.getOrdersByCustomerId(cid),HttpStatus.OK);
    }
*/
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> deleteOrder(@Valid @RequestParam UUID uuid){
        return new ResponseEntity(orderService.deleteOrder(uuid),HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Boolean> updateOrder(@Valid @RequestBody Order order){
        System.out.println(order);
        return new ResponseEntity(orderService.updateOrder(order),HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<UUID> createOrder(
            @Valid @RequestBody Order order,
            @Valid @RequestParam UUID address_id,
            @Valid @RequestParam UUID customer_id
    ){
        return new ResponseEntity(orderService.createOrder(order,address_id,customer_id),HttpStatus.OK);
    }

    @PutMapping(value = "/change-status")
    public ResponseEntity<Boolean> changeStatus(@Valid @RequestParam("id") UUID id, @Valid @RequestParam("status") String status){
        return new ResponseEntity(orderService.changeStatus(id,status),HttpStatus.OK);
    }




}
