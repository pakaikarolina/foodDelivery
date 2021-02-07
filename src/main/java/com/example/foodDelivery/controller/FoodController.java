package com.example.foodDelivery.controller;

import com.example.foodDelivery.entity.Food;
import com.example.foodDelivery.exception.ValidationException;
import com.example.foodDelivery.service.FoodService;
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
    @ResponseStatus(HttpStatus.OK)
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Food> getFood(@RequestParam(value = "page", required = false) Optional<Integer> page,
                              @RequestParam(value = "limit", required = false) Optional<Integer> limit) {
        log.info("Retrieving foods (page: {}, limit: {}) ...", page.isPresent() ? page.get() : "n.a.", limit.orElse(10));
        List<Food> foodlist;
        if (page.isPresent()) {
            foodlist = foodService.listFoods(
                    PageRequest.of(page.get(), limit.orElse(10)));
        } else {
            foodlist = foodService.listFoods();
        }
        log.debug("Found foods: {}", foodlist.size());
        return foodlist;
    }

    @GetMapping ("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Food getFoodById(@PathVariable ("id") String id) {
        log.info("Received find request by id: {}", id);

        Optional <Food> foundFoodById = foodService.getFoodById(id);
        if (foundFoodById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return foundFoodById.get();
    }
}
