package com.example.foodDelivery.service;

import com.example.foodDelivery.entity.CreateOrder;
import com.example.foodDelivery.entity.Food;
import com.example.foodDelivery.entity.Order;
import com.example.foodDelivery.entity.User;
import com.example.foodDelivery.exception.ValidationException;
import com.example.foodDelivery.repository.FoodRepository;
import com.example.foodDelivery.repository.OrderRepository;
import com.example.foodDelivery.repository.UserRepository;
import jdk.jshell.spi.ExecutionControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        }
        order.setOrderedBy(tempUser.get());
        orderRepository.save(order);
        return order;
    }
}
