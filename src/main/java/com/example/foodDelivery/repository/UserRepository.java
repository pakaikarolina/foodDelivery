package com.example.foodDelivery.repository;


import com.example.foodDelivery.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
