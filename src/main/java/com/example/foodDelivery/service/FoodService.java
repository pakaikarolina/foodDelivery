package com.example.foodDelivery.service;

import com.example.foodDelivery.entity.Food;
import com.example.foodDelivery.exception.ValidationException;
import com.example.foodDelivery.repository.FoodRepository;
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
public class FoodService {

    private static final Logger log = LoggerFactory.getLogger(FoodService.class);

    private final FoodRepository foodRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }


    public Food createNewFood(Food food) throws ValidationException {
        log.info("Creating new food based on {} ... ", food);

        if (food.getName() == null || food.getName().equals("") || food.getPrice() < 0) {
            log.error("Name must have a value ({name}) or price must be either 0 or more ({price})");
            throw new ValidationException("Name must have a value or price must be either 0 or more");
        }
        Food newFood = foodRepository.save(food);
        log.debug("Created food: {}", food);
        return newFood;
    }

    public Food updateFood(Food food, String id) throws ValidationException {
        log.info("Updating food based on {} ... ", food);

        if (food == null) {
            log.error("Food doesn't exist yet");
            throw new ValidationException("Please create food first");
        }
        Food updatedFood = foodRepository.save(food);
        log.debug("Updated food: {}", food);
        return updatedFood;
    }

    public List<Food> listFoods(Pageable pageable) {
        log.info("Listing foods (page information: {}) ...", pageable);
        Page<Food> foodPage = foodRepository.findAll(pageable);
        List<Food> foodList = foodPage.getContent();
        log.debug("Total count: {}, total pages: {}", foodPage.getTotalElements(), foodPage.getTotalPages());

        return foodList;
    }

    public List<Food> listFoods() {
        log.info("Listing all foods ...");
        List<Food> foodList = foodRepository.findAll();
        log.debug("Total count: {}, ", foodList.size());
        return foodList;
    }

    public Optional<Food> getFoodById(String id) {
        log.info("Listing food by id ...");
        return foodRepository.findById(id);
    }
}
