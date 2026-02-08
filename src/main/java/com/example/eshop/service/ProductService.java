package com.example.eshop.service;

import com.example.eshop.model.Product;

import java.util.List;

public interface ProductService {

    Product create(Product product);

    List<Product> findAll();
}
