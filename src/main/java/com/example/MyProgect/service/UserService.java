package com.example.MyProgect.service;

import com.example.MyProgect.model.User;

public interface UserService {

     void save(User user);

    User findByEmail(String email);
}
