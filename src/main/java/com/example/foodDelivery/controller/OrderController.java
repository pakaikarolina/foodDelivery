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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Order> getOrderList(@RequestParam(value = "page", required = false) Optional<Integer> page,
                              @RequestParam(value = "limit", required = false) Optional<Integer> limit) {
        log.info("Retrieving orders (page: {}, limit: {}) ...", page.isPresent() ? page.get() : "n.a.", limit.orElse(10));
        List<Order> orderList;
        if (page.isPresent()) {
            orderList = orderService.listOrders(
                    PageRequest.of(page.get(), limit.orElse(10)));
        } else {
            orderList = orderService.listOrders();
        }
        log.debug("Found orders: {}", orderList.size());
        return orderList;
    }

    @GetMapping ("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Order getOrderById(@PathVariable ("id") String id) {
        log.info("Received find request by id: {}", id);

        Optional <Order> foundOrderById = orderService.getOrderById(id);
        if (foundOrderById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return foundOrderById.get();
    }
}
