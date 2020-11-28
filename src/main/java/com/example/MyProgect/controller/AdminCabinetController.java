package com.example.MyProgect.controller;

import com.example.MyProgect.model.Product;
import com.example.MyProgect.repository.ProductRepository;
import com.example.MyProgect.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
public class AdminCabinetController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/adminCabinet")
    public String getAdminCabinet(Model model){
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "adminCabinet";
    }

    @PostMapping("/adminCabinet/addProduct")
    public String postAddProductDB(Product product){
        productRepository.save(product);
        return "redirect:/adminCabinet";
    }

    @PostMapping("/adminCabinet/{id}/updatePrDataBase")
    public String postUpdateProductDB(@PathVariable(value = "id") Long id,
                                      @RequestParam String category,
                                      @RequestParam String producer,
                                      @RequestParam String model,
                                      @RequestParam Double price,
                                      @RequestParam String description){
        Product product = productRepository.findById(id).orElseThrow();
        product.setCategory(category);
        product.setProducer(producer);
        product.setModel(model);
        product.setPrice(price);
        product.setDescription(description);
        productRepository.save(product);
        log.info("Product update : " + product.toString());
        return "redirect:/adminCabinet";
    }

    @PostMapping("/adminCabinet/{id}/deletePrDataBase")
    public String postDeleteProductDB(@PathVariable(value ="id") Long id){
        productRepository.deleteById(id);
        log.info("Product delete : " + id);
        return "redirect:/adminCabinet";
    }
}
