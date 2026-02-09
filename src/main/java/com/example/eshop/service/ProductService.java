package com.example.eshop.service;

import com.example.eshop.model.Product;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ProductService {

    Product create(Product product);

    List<Product> findAll();

    boolean deleteProduct(String productId);


}
