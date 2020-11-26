package com.example.MyProgect.controller;

import com.example.MyProgect.Dao.ProductDao;
import com.example.MyProgect.model.Product;
import com.example.MyProgect.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BucketController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDao productDao;


    @GetMapping("/store/{id}/putInBasket")
    public String addProductBucket(@PathVariable("id") Long id){

        Product product = productRepository.findById(id).orElseThrow();

        productDao.putInBucket(product);

        return "redirect:/store";
    }

    @GetMapping("/bucket")
    public String bucket(Model model){

        List<Product> productOutBucket = productDao.getOutBucket();

        Double totalPrise = productDao.totalPriseBucket();

        model.addAttribute("productOutBucket", productOutBucket);
        model.addAttribute("totalPrise", totalPrise);
        return "bucket";
    }

    @GetMapping("/bucket/{id}/deleteInBucket")
    public String delProductBucket(@PathVariable("id") Long id){

        productDao.deleteProductBucket(id);
        return "redirect:/bucket";
    }
}
