package com.example.MyProgect.controller;

import com.example.MyProgect.model.Product;
import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.ProductRepository;
import com.example.MyProgect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class StoreController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String store(Model model){

        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);

        return "store";
    }

}