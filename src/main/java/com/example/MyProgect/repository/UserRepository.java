package com.example.MyProgect.repository;

import com.example.MyProgect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User findByPassword(String password);
}
