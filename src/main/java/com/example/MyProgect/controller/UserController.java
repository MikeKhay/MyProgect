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
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model){

        User byEmail = userRepository.findByEmail(user.getEmail());

        if (byEmail != null){
            model.addAttribute("message", "User exists!!!");
            return "registration";
        }

        user.setRole(Role.USER);
        userRepository.save(user);
        log.info("Register new user " + user.toString());

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(User user, Model model){

        System.out.println(user.getPassword());
        System.out.println(user.getEmail());

//        User byEmailAndPassword = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        User user1 = userRepository.findByEmail(user.getEmail());

        if (user1 == null){
            model.addAttribute("message", "User not exists!!!");
            return "login";
        }
        return "hello";
    }

}
