package com.example.foodDelivery.service;

import com.example.foodDelivery.entity.Food;
import com.example.foodDelivery.exception.ValidationException;
import com.example.foodDelivery.repository.FoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService {

    private static final Logger log = LoggerFactory.getLogger(FoodService.class);

    private final FoodRepository foodRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }



    public Food createNewFood(Food food) throws ValidationException {
        log.info("Creating new food based on {} ... ", food);

        if(food.getName() == null || food.getName().equals("") && food.getPrice() < 0) {
            log.error("Name must have a value ({name}) or price must be either 0 or more ({price})");
            throw new ValidationException("Name must have a value or price must be either 0 or more");
        }
        Food newFood = foodRepository.save(food);
        log.debug("Created food: {}", food);
        return newFood;
    }
}
