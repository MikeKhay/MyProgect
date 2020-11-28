package com.example.MyProgect.controller;

import com.example.MyProgect.model.Role;
import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
public class LoginRegistController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    private String getLoginUser(){
        return "login";
    }

    @PostMapping("/login")
    private String postLoginUser(User user, Model model){
        User userDb = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if(userDb == null){
            model.addAttribute("message", "Such user is not registered. Or entered incorrect information.");
            return "login";
        }
        log.info("User login " + user.toString());
        return "redirect:/store";
    }

    @GetMapping("/registration")
    private String getRegisterUser(){
        return "registration";
    }

    @PostMapping("/registration")
    private String postRegistrationUser(User user, Model model){
        User userEmail = userRepository.findByEmail(user.getEmail());
        if(userEmail != null){
            model.addAttribute("message", "A user with this email is registered");
            return "registration";
        }
        User userPassword = userRepository.findByPassword(user.getPassword());
        if(userPassword != null){
            model.addAttribute("message", "A user with this password is registered");
            return "registration";
        }
        log.info("New User save " + user.toString());
        user.setRole(Role.USER);
        userRepository.save(user);

        return "redirect:/login";
    }

}
