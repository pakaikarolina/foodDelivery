package com.example.foodDelivery.entity;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrder {
    List<String> foodId;
    String userId;
}
