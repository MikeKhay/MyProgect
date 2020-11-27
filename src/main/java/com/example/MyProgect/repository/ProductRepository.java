package com.example.MyProgect.repository;

import com.example.MyProgect.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
