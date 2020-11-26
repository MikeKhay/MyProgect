package com.example.MyProgect.controller;

import com.example.MyProgect.Dao.ProductDao;
import com.example.MyProgect.model.Order;
import com.example.MyProgect.model.Product;
import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.OrderRepository;
import com.example.MyProgect.repository.ProductRepository;
import com.example.MyProgect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDao productDao;

    @PostMapping("/order")
    public String order(@RequestParam Long numberTel,
                        @RequestParam String lastName,
                        @RequestParam String firstName,
                        @RequestParam String city,
                        @RequestParam String address){

        User user = new User();
        user.setNumberTel(numberTel);
        user.setLastName(lastName);
        user.setFirstName(firstName);

        userRepository.save(user);

        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(productDao.totalPriseBucket());
        order.setOrderCity(city);
        order.setOrderAddress(address);

        List<Product> getOutBucket = productDao.getOutBucket();

        for (Product p: getOutBucket) {
            Product pr = productRepository.findById(p.getId()).orElseThrow();
            order.setProducts(Arrays.asList(pr));
        }

        orderRepository.save(order);

        System.out.println(order.toString());


        return "redirect:/store";
    }
}
