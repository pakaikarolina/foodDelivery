package com.example.foodDelivery.service;

import com.example.foodDelivery.entity.CreateOrder;
import com.example.foodDelivery.entity.Food;
import com.example.foodDelivery.entity.Order;
import com.example.foodDelivery.entity.User;
import com.example.foodDelivery.exception.ValidationException;
import com.example.foodDelivery.repository.FoodRepository;
import com.example.foodDelivery.repository.OrderRepository;
import com.example.foodDelivery.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        Order order = new Order();
        for (String id : createOrder.getFoodId()) {
            Optional<Food> tempFood = foodRepository.findById(id);
            if (tempFood.isEmpty()) {
                throw new ValidationException("This food doesn't exist");
            }
            order.getFoods().add(tempFood.get());
        }
        Optional<User> tempUser = userRepository.findById(createOrder.getUserId());
        if (tempUser.isEmpty()) {
            throw new ValidationException("This user doesn't exist");
        }
        order.setOrderedBy(tempUser.get());
        orderRepository.save(order);
        return order;
    }


}
