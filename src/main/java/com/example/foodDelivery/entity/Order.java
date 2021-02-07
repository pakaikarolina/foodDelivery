package com.example.foodDelivery.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    @ManyToMany (mappedBy = "orders")
    private List<Food> foods = new ArrayList<>(); //null nem lehet amikor létrejön az order
    @ManyToOne
    @JoinColumn(name = "orderedBy")
    private User orderedBy;

}
