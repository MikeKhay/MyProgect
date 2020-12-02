package com.example.MyProgect.repository;

import com.example.MyProgect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    
    boolean findByPassword(String password);

    boolean findByNumberTel(Long numberTel);
}
