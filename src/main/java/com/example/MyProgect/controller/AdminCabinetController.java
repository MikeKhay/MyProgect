package com.example.MyProgect.controller;

import com.example.MyProgect.model.Product;
import com.example.MyProgect.model.Role;
import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.ProductRepository;
import com.example.MyProgect.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Log4j2
@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminCabinetController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/adminCabinet")
    public String adminCabinet(Model model){
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("roles", Role.values());
        model.addAttribute("products", productRepository.findAll());
        return "adminCabinet";
    }

//    @PostMapping("/adminCabinet/addProduct")
//    public String addProductDB(@RequestParam String category,
//                               @RequestParam String producer,
//                               @RequestParam String modell,
//                               @RequestParam Double price,
//                               @RequestParam String description){
//        Product product = new Product();
//        if(category != null && producer != null && modell != null && price != null) {
//            product.setCategory(category);
//            product.setProducer(( producer ));
//            product.setModel(modell);
//            product.setPrice(price);
//            product.setDescription(description);
//            productRepository.save(product);
//        }
//        return "adminCabinet";
//    }

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
                                             @RequestParam String role){

        User byUsername = userRepository.findById(id).orElseThrow();
        byUsername.setUsername(username);
        byUsername.setCity(city);
        byUsername.setAddress(address);
        log.info("New role : " + role);
//        if(role.equals("admin")){
//            byUsername.getRoles().clear();
//            byUsername.setRoles(Collections.singleton(Role.ADMIN));
//        }
//        if(role.equalsIgnoreCase("user")){
//            byUsername.getRoles().clear();
//            byUsername.setRoles(Collections.singleton(Role.USER));
//        }
        userRepository.save(byUsername);
        return "redirect:/adminCabinet";

    }
}
