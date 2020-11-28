package com.example.MyProgect.controller;

import com.example.MyProgect.model.Product;
import com.example.MyProgect.model.Role;
import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.ProductRepository;
import com.example.MyProgect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminCabinetController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/adminCabinet")
    public String adminCabinet(Model model){

//        List<User> users = userRepository.findAll();
//        users.forEach(System.out::println);
//        model.addAttribute("users", users);

        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "adminCabinet";
    }

    @PostMapping("/adminCabinet")
    public String addProductDB(@RequestParam String category,
                               @RequestParam String producer,
                               @RequestParam String model,
                               @RequestParam Double price,
                               @RequestParam String description){
        Product product = new Product();
        product.setCategory(category);
        product.setProducer((producer));
        product.setModel(model);
        product.setPrice(price);
        product.setDescription(description);
        productRepository.save(product);
        return "adminCabinet";
    }

    @PostMapping("/adminCabinet/{id}/deletePrDataBase")
    public String postAdminCabinetDeleteProduct(@PathVariable(value = "id") Long id, Model model){

        System.out.println(id);
        productRepository.deleteById(id);

        return "redirect:/adminCabinet";
    }

    @PostMapping("/adminCabinet/{id}/updatePrDataBase")
    public String postAdminCabinetUpdateProduct(@PathVariable(value = "id") Long id,
                                                @RequestParam String category,
                                                @RequestParam String producer,
                                                @RequestParam String model,
                                                @RequestParam Double price,
                                                @RequestParam String description){
        Product product = productRepository.findById(id).orElseThrow();

        product.setCategory(category);
        product.setProducer((producer));
        product.setModel(model);
        product.setPrice(price);
        product.setDescription(description);

        productRepository.save(product);

        return "redirect:/adminCabinet";
    }

    @PostMapping("/adminCabinet/{id}/updateUsDataBase")
    public String postAdminCabinetUpdateUser(@PathVariable(value = "id") Long id,
                                             @RequestParam String username,
                                             @RequestParam String city,
                                             @RequestParam String address,
                                             @RequestParam boolean active,
                                             @RequestParam Set<Role> role){
        User byUsername = userRepository.findByUsername(username);
        byUsername.setUsername(username);
        byUsername.setCity(city);
        byUsername.setAddress(address);
        byUsername.setActive(active);
        byUsername.setRoles(role);

        userRepository.save(byUsername);
        return "adminCabinet";

    }
}
