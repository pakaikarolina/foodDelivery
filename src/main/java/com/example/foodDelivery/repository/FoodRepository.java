package com.example.foodDelivery.repository;

import com.example.foodDelivery.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends PagingAndSortingRepository<Food, String> {
    @Override
    List<Food> findAll();

    @Override
    Page<Food> findAll(Pageable pageable);
}
