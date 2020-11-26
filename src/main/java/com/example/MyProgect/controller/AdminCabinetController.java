package com.example.MyProgect.controller;

import com.example.MyProgect.model.Product;
import com.example.MyProgect.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminCabinetController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/adminCabinet")
    public String adminCabinet(){
        return "adminCabinet";
    }

    @GetMapping("/adminCabinet/adminProduct")
    public String adminCabinetAddProduct(Model model){
            Iterable<Product> products = productRepository.findAll();
            model.addAttribute("products", products);
        return "adminProduct";
    }

    @PostMapping("/adminCabinet/adminProduct")
    public String postAdminCabinetAddProduct(@RequestParam String category,
                                              @RequestParam String producer,
                                              @RequestParam String model,
                                              @RequestParam Double price,
                                              @RequestParam String description
    ){
        Product product = new Product(category, producer, model, price, description);
        productRepository.save(product);
        return "redirect:/adminCabinet/adminProduct";
    }

    @PostMapping("/adminCabinet/adminProduct/{id}/deletePrDataBase")
    public String postAdminCabinetDeleteProduct(@PathVariable(value = "id") Long id, Model model){

        System.out.println(id);
        productRepository.deleteById(id);

        return "redirect:/adminCabinet/adminProduct";
    }

    @PostMapping("/adminCabinet/adminProduct/{id}/updatePrDataBase")
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

        return "redirect:/adminCabinet/adminProduct";
    }
}
