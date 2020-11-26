package com.example.MyProgect.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String category;
    private String producer;
    private String model;
    private Double price;
    private String description;

    public Product(String category, String producer, String model, Double price, String description) {
        this.category = category;
        this.producer = producer;
        this.model = model;
        this.price = price;
        this.description = description;
    }
}
