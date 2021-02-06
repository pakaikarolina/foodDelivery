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
        log.info("Received create new food request {} ... ", food);
        try {
            Food newFood = foodService.createNewFood(food);
            log.debug("The new food is: {}", newFood);
            return newFood;
        } catch (ValidationException e) {
            log.error("Error when creating new food: " + e.getMessage());
            throw new ResponseStatusException(e.getHttpStatus(), e.getMessage());
        }

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Food updateFood(@RequestBody Food food, @PathVariable ("id")  String id) {
        log.info("Received update food request {} ... ", food);
        try {
            Food updatedFood = foodService.updateFood(food, id);
            log.debug("The new food is: {}", updatedFood);
            return updatedFood;
        } catch (ValidationException e) {
            log.error("Error when updating food: " + e.getMessage());
            throw new ResponseStatusException(e.getHttpStatus(), e.getMessage());
        }
    }
}
