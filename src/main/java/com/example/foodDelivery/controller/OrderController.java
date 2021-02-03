package com.example.foodDelivery.controller;

import com.example.foodDelivery.service.FoodService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final FoodService foodService;

    public OrderController(FoodService foodService) {
        this.foodService = foodService;
    }

}
