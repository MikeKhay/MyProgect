package com.example.MyProgect.controller;

import com.example.MyProgect.model.Role;
import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Log4j
@Controller
public class LoginRegisController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model){

        User byEmail = userRepository.findByUsername(user.getUsername());

        if (byEmail != null){
            model.addAttribute("message", "User Exist!!!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Arrays.asList(Role.USER));
        log.info("Registration new user : " + user.getId());
        userRepository.save(user);

        return "redirect:/login";
    }
}
