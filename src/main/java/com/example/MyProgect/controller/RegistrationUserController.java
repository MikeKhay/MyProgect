package com.example.MyProgect.controller;

import com.example.MyProgect.model.Role;
import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class RegistrationUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String loginUser(Model model){
        return "registration";
    }

    @PostMapping("/registration")
    public String postLoginUser(@RequestParam String email,
                                @RequestParam String password,
                                Model model){

        User userFromDb = userRepository.findByEmail(email);
        if(userFromDb != null){
            model.addAttribute("message", "User EXISTS!!!!");
            return "registration";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(Collections.singleton(Role.USER));
        System.out.println(user.toString());

        userRepository.save(user);
        return "redirect:/login";
    }
}
