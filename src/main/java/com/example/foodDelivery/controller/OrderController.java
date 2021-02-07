package com.example.foodDelivery.controller;

import com.example.foodDelivery.entity.CreateOrder;
import com.example.foodDelivery.entity.Food;
import com.example.foodDelivery.entity.Order;
import com.example.foodDelivery.exception.ValidationException;
import com.example.foodDelivery.service.FoodService;
import com.example.foodDelivery.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createNewOrder(@RequestBody CreateOrder createOrder) {
        log.info("Received create new order request {} ... ", createOrder);
        try {
            Order newOrder = orderService.createNewOrder(createOrder);
            log.debug("The new order is: {}", newOrder);
            return newOrder;
        } catch (ValidationException e) {
            log.error("Error when creating new order: " + e.getMessage());
            throw new ResponseStatusException(e.getHttpStatus(), e.getMessage());
        }
    }
}
