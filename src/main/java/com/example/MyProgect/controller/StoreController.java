package com.example.MyProgect.controller;

import com.example.MyProgect.model.Product;
import com.example.MyProgect.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StoreController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/store")
    public String store(Model model){
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);

        return "store";
    }
}
