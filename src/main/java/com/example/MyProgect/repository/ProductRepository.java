package com.example.MyProgect.repository;

import com.example.MyProgect.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findFirstById(long id);
}
