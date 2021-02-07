package com.example.foodDelivery.service;

import com.example.foodDelivery.entity.*;
import com.example.foodDelivery.entity.Order;
import com.example.foodDelivery.exception.ValidationException;
import com.example.foodDelivery.repository.FoodRepository;
import com.example.foodDelivery.repository.OrderRepository;
import com.example.foodDelivery.repository.UserRepository;
import jdk.jshell.spi.ExecutionControl;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    public Order createNewOrder(CreateOrder createOrder) throws ValidationException {
        Optional<User> tempUser;
        if (createOrder.getUserId() == null || createOrder.getUserId().equals("")) {
            tempUser = Optional.of(new User());
        } else {
            tempUser = userRepository.findById(createOrder.getUserId()); //ID alapján kikeresem a usert, eltárolom
        }
        if (tempUser.isEmpty()) {
            log.info("Given id is: {}", createOrder.getUserId());
            throw new ValidationException("User with the given id doesn't exist");
        }

        if (createOrder.getFoodIdList() == null || createOrder.getFoodIdList().isEmpty()) {
            throw new ValidationException("Invalid order, orderlist is empty");
        }
        Order order = new Order();

        for (String id : createOrder.getFoodIdList()) {
            Optional<Food> tempFood = foodRepository.findById(id);
            if (tempFood.isEmpty()) {
                throw new ValidationException("This food doesn't exist");
            }
            order.getFoods().add(tempFood.get());
            tempFood.get().getOrders().add(order);
        }
        order.setOrderedBy(tempUser.get());
        orderRepository.save(order);
        return order;
    }

    public List<Order> listOrders(Pageable pageable) {
        log.info("Listing orders (page information: {}) ...", pageable);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        List<Order> orderList = orderPage.getContent();
        log.debug("Total count: {}, total pages: {}", orderPage.getTotalElements(), orderPage.getTotalPages());
        return orderList;
    }

    public List<Order> listOrders() {
        log.info("Listing all orders ...");
        List<Order> orderList = orderRepository.findAll();
        log.debug("Total count: {}, ", orderList.size());
        return orderList;
    }


    public Optional<Order> getOrderById(String id) {
        log.info("Listing order by id ...");
        return orderRepository.findById(id);
    }
}
