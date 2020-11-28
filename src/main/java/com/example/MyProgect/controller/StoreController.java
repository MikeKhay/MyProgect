package com.example.MyProgect.controller;

import com.example.MyProgect.model.Product;
import com.example.MyProgect.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@Controller
public class StoreController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/store")
    public String getStore(Model model){
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "store";
    }


}
