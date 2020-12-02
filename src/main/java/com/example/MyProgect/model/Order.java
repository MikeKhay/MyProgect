package com.example.MyProgect.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    private Double totalPrice;
    @NotNull
    private String city;
    @NotNull
    private String address;
    @NotNull
    private Long numberTel;

    @PrePersist
    private void onCreate(){
        data = new Date();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "product_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    public Order(Double totalPrice, User user, List<Product> products) {
        this.totalPrice = totalPrice;
        this.user = user;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public Long getNumberTel() {
        return numberTel;
    }

    public User getUser() {
        return user;
    }

    public List<Product> getProducts() {
        return products;
    }
}