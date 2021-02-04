package com.example.foodDelivery.controller;

import com.example.foodDelivery.entity.Food;
import com.example.foodDelivery.exception.ValidationException;
import com.example.foodDelivery.service.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    private FoodService foodService;

    private static final Logger log = LoggerFactory.getLogger(FoodController.class);

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Food createNewFood(@RequestBody Food food) {
        log.info("Received createNewFood request {} ... ", food);
        try {
            Food newFood = foodService.createNewFood(food);
            log.debug("The new food is: {}", newFood);
            return newFood;
        } catch (ValidationException e) {
            log.error("Error when creating new food: " + e.getMessage());
            throw new ResponseStatusException(e.getHttpStatus(), e.getMessage());
        }

    }
}
