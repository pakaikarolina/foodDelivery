package com.example.foodDelivery.entity;

import lombok.*;

import java.util.List;

@Value
@RequiredArgsConstructor

public class CreateOrder {
    List<String> foodIdList;
    String userId;
}
