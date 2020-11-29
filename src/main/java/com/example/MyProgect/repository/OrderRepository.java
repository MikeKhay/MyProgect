package com.example.MyProgect.repository;

import com.example.MyProgect.model.Order;
import com.example.MyProgect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);
}

