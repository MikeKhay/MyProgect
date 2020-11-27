package com.example.MyProgect.controller;

import com.example.MyProgect.model.Product;
import com.example.MyProgect.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.Optional;

@Controller
public class StoreController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/store")
    public String store(Principal principal){

        System.out.println(principal);

//        Iterable<Product> products = productRepository.findAll();
//        model.addAttribute("products", products);

        return "store";
    }
}
