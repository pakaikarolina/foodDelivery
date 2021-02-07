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
    private List<Food> foods = new ArrayList<>(); //null ne legyen amikor létrejön az order, hanem üres lista
    @ManyToOne(cascade = CascadeType.ALL) //ha elmentünk egy ordert, ha van benne egy user, azt is elmenti
    @JoinColumn(name = "orderedBy")
    private User orderedBy;
}
