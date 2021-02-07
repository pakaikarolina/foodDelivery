package com.example.foodDelivery.repository;

import com.example.foodDelivery.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, String> {
}
