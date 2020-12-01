package com.example.MyProgect.controller;

import com.example.MyProgect.model.Order;
import com.example.MyProgect.model.Product;
import com.example.MyProgect.model.Role;
import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.OrderRepository;
import com.example.MyProgect.repository.ProductRepository;
import com.example.MyProgect.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/adminCabinet")
    public String adminCabinet(Model model){
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("roles", Role.values());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "adminCabinet";
    }


    /////////////////////////////////// ORDER CONTROL /////////////////////////////////////////////////////
    @PostMapping("/adminCabinet/{id}/updateOrderDataBase")
    public String postUpdateOrderDataBase(@PathVariable(name = "id") Long id,
                                          @RequestParam String city,
                                          @RequestParam String address,
                                          @RequestParam Long numberTel){
        Order order = orderRepository.findById(id).orElseThrow();
        order.setCity(city);
        order.setAddress(address);
        order.setNumberTel(numberTel);
        orderRepository.save(order);
        return "redirect:/adminCabinet";
    }

    @PostMapping("/adminCabinet/{orderId}/{productId}/updateProductOrderDataBase")
    public String updateProductOrderDataBase(@PathVariable(name = "orderId") Long orderId,
                                             @PathVariable(name = "productId") Long productId){
        Order order = orderRepository.findById(orderId).orElseThrow();
        List<Product> products = order.getProducts();
        double totalPrice = 0;
        for (Product product: products) {
            if(product.getId()==productId){
                products.remove(product);
                totalPrice += product.getPrice();
            }
        }

        order.setTotalPrice(totalPrice);
        order.setProducts(products);
        orderRepository.save(order);
        return "redirect:/adminCabinet";
    }

    @PostMapping("/adminCabinet/{id}/deleteOrderDataBase")
    public String deleteOrderDataBase(@PathVariable(name = "id") Long id){
//        List<Order> orders = orderRepository.findAll();
//        orders.removeIf(order -> order.getId() == id);
//        orderRepository.saveAll(orders);

        orderRepository.deleteById(id);
        return "redirect:/adminCabinet";
    }



    /////////////////////////////////// PRODUCT CONTROL /////////////////////////////////////////////////////
    @PostMapping("/adminCabinet/addProduct")
    public String addProductDB(@RequestParam String category,
                               @RequestParam String producer,
                               @RequestParam String model,
                               @RequestParam Double price,
                               @RequestParam String description){
        Product product = new Product();
        if(category != null && producer != null && model != null && price != null) {
            product.setCategory(category);
            product.setProducer(( producer ));
            product.setModel(model);
            product.setPrice(price);
            product.setDescription(description);
            productRepository.save(product);
        }
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


    /////////////////////////////////// USER CONTROL /////////////////////////////////////////////////////
    @PostMapping("/adminCabinet/{id}/updateUsDataBase")
    public String postAdminCabinetUpdateUser(@PathVariable(value = "id") Long id,
                                             @RequestParam String username,
                                             @RequestParam String role){

        User byUsername = userRepository.findById(id).orElseThrow();
        byUsername.setUsername(username);
        log.info("New role : " + role);
        if(role.equalsIgnoreCase("admin")){
            byUsername.getRoles().clear();
            byUsername.getRoles().add(Role.ADMIN);
        }
        if(role.equalsIgnoreCase("user")){
            byUsername.getRoles().clear();
            byUsername.getRoles().add(Role.USER);        }
        userRepository.save(byUsername);
        return "redirect:/adminCabinet";

    }
}
