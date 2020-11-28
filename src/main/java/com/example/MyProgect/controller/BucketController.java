package com.example.MyProgect.controller;

import com.example.MyProgect.model.Bucket;
import com.example.MyProgect.model.Product;
import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.BucketRepository;
import com.example.MyProgect.repository.ProductRepository;
import com.example.MyProgect.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Arrays;

@Log4j2
@Controller
public class BucketController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BucketRepository bucketRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/store/{id}/putInBucket")
    public String postPutInBucket(@PathVariable(name = "id") Long id, Principal principal){

        User user = userRepository.findByUsername(principal.getName());
        log.info(user.toString());

        Product product = productRepository.findById(id).orElseThrow();
        log.info(product.toString());

        Bucket bucket = new Bucket();
        bucket.setUser(user);
        bucket.setProducts(Arrays.asList(product));
        System.out.println(bucket.toString());

        bucketRepository.save(bucket);

        return "store";
    }
}
