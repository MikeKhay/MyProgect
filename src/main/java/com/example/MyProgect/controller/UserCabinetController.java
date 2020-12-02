package com.example.MyProgect.controller;

import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.OrderRepository;
import com.example.MyProgect.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Log4j
@Controller
public class UserCabinetController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/userCabinet")
    public String getUserCabinet(Principal principal, Model model){
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("orders", orderRepository.findAllByUser(user));
        return "userCabinet";
    }

    @PostMapping("/userCabinet/updateUserDataBase")
    public String postUserCabinetUpdate(@RequestParam String password,
                                        @RequestParam String lastName,
                                        @RequestParam String firstName,
                                        @RequestParam String city,
                                        @RequestParam String address,
                                        @RequestParam Long numberTel,
                                        Principal principal) throws NonUniqueResultException{
        User user = userRepository.findByUsername(principal.getName());
//        if(userRepository.findByPassword(password) == false){
//            if(userRepository.findByNumberTel(numberTel) == false){
                user.setPassword(password);
                user.setLastName(lastName);
                user.setFirstName(firstName);
                user.setCity(city);
                user.setAddress(address);
                user.setNumberTel(numberTel);
                userRepository.save(user);
                log.info("Update information user : " + user.getId());
//            }
//        }

        return "redirect:/userCabinet";
    }
}
