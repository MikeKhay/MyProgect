package com.example.MyProgect.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String category;
    @NotNull
    private String producer;
    @NotNull
    private String model;
    @NotNull
    private Double price;
    @NotNull
    private String description;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.DETACH)
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(mappedBy = "products", cascade = CascadeType.DETACH)
    private List<User> users = new ArrayList<>();

    public Product(String category, String producer, String model, Double price, String description) {
        this.category = category;
        this.producer = producer;
        this.model = model;
        this.price = price;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", producer='" + producer + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}