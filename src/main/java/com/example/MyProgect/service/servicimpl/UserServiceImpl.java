package com.example.MyProgect.service.servicimpl;

import com.example.MyProgect.model.Role;
import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.UserRepository;
import com.example.MyProgect.service.UserService;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        user.setRoles(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("Stored user {}", user.getEmail());
    }

    @Override
    public User findByEmail(String email) {
        log.info("Looking for user with email " + email);
        return userRepository.findByEmail(email);
    }
}
