package com.example.MyProgect.controller;

import com.example.MyProgect.model.Order;
import com.example.MyProgect.model.Product;
import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.OrderRepository;
import com.example.MyProgect.repository.ProductRepository;
import com.example.MyProgect.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Log4j2
@Controller
public class BucketController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/bucket")
    public String bucketUser(Principal principal, Model model){
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("product", user.getProducts());

        List<Product> products = user.getProducts();
        double totalPrice = 0;
        for (Product p: products) {
            totalPrice += p.getPrice();
        }
        model.addAttribute("totalPrice", totalPrice);
        return "bucket";
    }

    @GetMapping("/bucket/{id}/putInBucket")
    public String postPutInBucket(@PathVariable(name = "id") Long id, Principal principal){
        Product product = productRepository.findById(id).orElseThrow();
        System.out.println(product);
        User user = userRepository.findByUsername(principal.getName());
        user.getProducts().add(product);
        userRepository.save(user);
        System.out.println(user);
        return "store";
    }

    @PostMapping("/bucket/{id}/deleteInBucket")
    public String postDeleteInBucket(@PathVariable(name = "id") Long id, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        List<Product> products = user.getProducts();
        products.removeIf(product -> product.getId() == id);
        user.setProducts(products);
        userRepository.save(user);
        return "redirect:/bucket";
    }

    @PostMapping("/bucket/buy")
    public String postToBuy(Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        if (user.getProducts()==null){
            return "store";
        }
        else {
            Order order = new Order();
            List<Product> products = user.getProducts();
            double totalPrice = 0;
            for (Product p : products) {
                totalPrice += p.getPrice();
                Product product = productRepository.findById(p.getId()).orElseThrow();
                order.getProducts().add(product);
            }
            order.setTotalPrice(totalPrice);
            order.setUser(user);
            orderRepository.save(order);
            user.getProducts().clear();
            userRepository.save(user);
        }
        return "store";
    }



}
