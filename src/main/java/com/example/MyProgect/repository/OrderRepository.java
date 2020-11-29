package com.example.MyProgect.repository;

import com.example.MyProgect.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
