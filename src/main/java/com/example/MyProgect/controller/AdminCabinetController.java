package com.example.MyProgect.controller;

import com.example.MyProgect.model.Order;
import com.example.MyProgect.model.Product;
import com.example.MyProgect.model.Role;
import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.OrderRepository;
import com.example.MyProgect.repository.ProductRepository;
import com.example.MyProgect.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Log4j
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
//        model.addAttribute("users", userRepository.findAll());
//        model.addAttribute("roles", Role.values());
//        model.addAttribute("products", productRepository.findAll());
//        model.addAttribute("orders", orderRepository.findAll());
        return "adminCabinet";
    }


    /////////////////////////////////// ORDER CONTROL /////////////////////////////////////////////////////
    @GetMapping("/adminCabinet/adminOrder")
    public String getAdminOrderControl(Model model){
        model.addAttribute("orders", orderRepository.findAll());
        return "adminOrder";
    }

    @PostMapping("/adminCabinet/{id}/updateOrderDataBase")
    public String postUpdateOrderDataBase(@PathVariable(name = "id") Long id,
                                          @RequestParam String city,
                                          @RequestParam String address,
                                          @RequestParam Long numberTel){
        Order order = orderRepository.findById(id).orElseThrow();
        order.setCity(city);
        order.setAddress(address);
        order.setNumberTel(numberTel);
        log.info("Update order : " + order.getId());
        orderRepository.save(order);
        return "adminOrder";
    }

    @PostMapping("/adminCabinet/{orderId}/{productId}/updateProductOrderDataBase")
    public String updateProductOrderDataBase(@PathVariable(name = "orderId") Long orderId,
                                             @PathVariable(name = "productId") Long productId){
        Order order = orderRepository.findById(orderId).orElseThrow();
        List<Product> products = order.getProducts();
        for (Product product: products) {
            if(product.getId() == productId){
                if (products.remove(product)){
                    break;
                }
            }
        }
        double totalPrice = 0;
        for (Product product: products) {
            totalPrice += product.getPrice();
        }

        order.setTotalPrice(totalPrice);
        order.setProducts(products);
        log.info("Update product in order : " + order.getId());
        orderRepository.save(order);
        return "adminOrder";
    }


    //Доробити ордера. повністю не видаляються.
    @PostMapping("/adminCabinet/{id}/deleteOrderDataBase")
    public String deleteOrderDataBase(@PathVariable(name = "id") Long id){
//        List<Order> orders = orderRepository.findAll();
//        Order order1 = orderRepository.findById(id).orElseThrow();
//        for (Order order : orders) {
//            if(order.getId() == id){
//                order.remove();
//            }
//        }
//        Iterator iterator = orders.iterator();
//        while (iterator.hasNext()) {
//            Object element = iterator.next();
//            if (order1.equals(element)) {
//                iterator.remove();
//            }
//        }
//
//        orderRepository.saveAll(orders);
//        log.info("Delete order : " + id);
//        orderRepository.deleteById(id);
//        http://localhost:8080/adminCabinet/8/deleteOrderDataBase
        return "adminOrder";
    }



    /////////////////////////////////// PRODUCT CONTROL /////////////////////////////////////////////////////
    @GetMapping("/adminCabinet/adminProduct")
    public String getAdminCabinetControlProduct(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "adminProduct";
    }

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
            log.info("Add new product : " + product.getId());
            productRepository.save(product);
        }
        return "adminCabinet";
    }

    @GetMapping("/adminCabinet/{id}/searchPrDataBase")
    public String getAdminCabinetSearchProduct(@PathVariable(value = "id") Long id, Model model){
        model.addAttribute("product", productRepository.findById(id).orElseThrow());
        return "adminProductUpdate";
    }

    @GetMapping("/adminCabinet/{id}/deletePrDataBase")
    public String getAdminCabinetDeleteProduct(@PathVariable(value = "id") Long id){
        log.info("Delete product : " + id);
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
        log.info("Update product : " + product.getId());
        productRepository.save(product);

        return "redirect:/adminCabinet";
    }


    /////////////////////////////////// USER CONTROL /////////////////////////////////////////////////////
    @GetMapping("/adminCabinet/adminUser")
    public String getAdminCabinetUpdateUser(Model model){
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("roles", Role.values());
        return "adminUser";
    }

    @PostMapping("/adminCabinet/{id}/updateUsDataBase")
    public String postAdminCabinetUpdateUser(@PathVariable(value = "id") Long id,
                                             @RequestParam String username,
                                             @RequestParam String role){

        User byUsername = userRepository.findById(id).orElseThrow();
        byUsername.setUsername(username);
        if(role.equalsIgnoreCase("admin")){
            byUsername.getRoles().clear();
            byUsername.getRoles().add(Role.ADMIN);
        }
        if(role.equalsIgnoreCase("user")){
            byUsername.getRoles().clear();
            byUsername.getRoles().add(Role.USER);        }
        log.info("New role : " + role + " in person : " + byUsername.getId());
        userRepository.save(byUsername);
        return "adminUser";

    }
}
