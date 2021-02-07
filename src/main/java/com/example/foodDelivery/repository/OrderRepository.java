package com.example.foodDelivery.repository;

import com.example.foodDelivery.entity.Food;
import com.example.foodDelivery.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
}
