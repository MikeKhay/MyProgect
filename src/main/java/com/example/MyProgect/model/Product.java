package com.example.MyProgect.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String category;
    private String producer;
    private String model;
    private Double price;
    private String description;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name="bucket_id")
//    private Bucket bucket;

    @ManyToMany(mappedBy = "products")
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