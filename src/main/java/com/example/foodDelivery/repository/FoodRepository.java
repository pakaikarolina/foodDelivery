package com.example.foodDelivery.repository;

import com.example.foodDelivery.entity.Food;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends PagingAndSortingRepository<Food, String> {

}
