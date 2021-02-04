package com.example.foodDelivery.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.util.List;


@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    @ManyToMany (mappedBy = "orders")
    private List<Food> foods;
    @ManyToOne
    @JoinColumn(name = "orderedBy")
    private User orderedBy;
}
