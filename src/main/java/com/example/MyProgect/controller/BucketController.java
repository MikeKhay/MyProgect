package com.example.MyProgect.controller;

import com.example.MyProgect.model.Order;
import com.example.MyProgect.model.Product;
import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.OrderRepository;
import com.example.MyProgect.repository.ProductRepository;
import com.example.MyProgect.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Log4j
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
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
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
        log.info("Add product in bucket : " + product.getId() + " in person : " + user.getId());
        return "store";
    }

    @PostMapping("/bucket/{id}/deleteInBucket")
    public String postDeleteInBucket(@PathVariable(name = "id") Long id, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        List<Product> products = user.getProducts();
        products.removeIf(product -> product.getId() == id);
        user.setProducts(products);
        log.info("Removing the product from the cart by the user : " + user.getId());
        userRepository.save(user);
        return "redirect:/bucket";
    }

    @PostMapping("/bucket/buy")
    public String postToBuy(@RequestParam String city,
                            @RequestParam String address,
                            @RequestParam Long numberTel,
                            Principal principal){
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
            order.setCity(city);
            order.setAddress(address);
            order.setNumberTel(numberTel);
            order.setTotalPrice(totalPrice);
            order.setUser(user);
            orderRepository.save(order);
            user.getProducts().clear();
            log.info("Add new order : " + order.getId());
            userRepository.save(user);
        }
        return "store";
    }



}
