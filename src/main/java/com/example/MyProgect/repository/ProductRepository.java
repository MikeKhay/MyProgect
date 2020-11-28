package com.example.MyProgect.repository;

import com.example.MyProgect.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);
}
