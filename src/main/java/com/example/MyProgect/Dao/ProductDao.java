package com.example.MyProgect.Dao;

import com.example.MyProgect.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDao {

    List<Product> productList = new ArrayList<>();

    public void putInBucket(Product product) {
        productList.add(product);
    }

    public List<Product> getOutBucket() {
        return productList;
    }

    public void deleteProductBucket(Long id) {
        productList.removeIf(product -> product.getId() == id);
    }

    public Double totalPriseBucket() {
        double totalPrise = 0;

        for (Product p: productList) {
            totalPrise+=p.getPrice();
        }
        return totalPrise;
    }

    public Long getIdProduct(){
        Long getIdOneProduct = 0l;
        for (Product p: productList) {
            getIdOneProduct=p.getId();
        }
        return getIdOneProduct;
    }
}
