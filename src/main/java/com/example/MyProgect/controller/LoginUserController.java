package com.example.MyProgect.controller;

import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginUser(Model model){
        return "login";
    }

    @PostMapping("/login")
    public String loginPostUser(@RequestParam String email,
                                @RequestParam String password,
                                Model model) {
        User userFromDb = userRepository.findByEmailAndPassword(email, password);

        if (userFromDb == null){
            model.addAttribute("message","The user is not registered or enter the correct login and password.");
            return "login";
        }

//        System.out.println(userFromDb.getRoles());
//
//        String[] s = {"ADMIN"};
//        if (userFromDb.getRoles().equals(s))
//        {
//            System.out.println("Заходить в перевірку на узера");
//            return "cabinetAdmin";
//        }

        System.out.println(userFromDb.toString());
//        model.addAttribute("user", userFromDb);
//        return "redirect:/adminCabinet";
        return "/adminCabinet";
    }
}
