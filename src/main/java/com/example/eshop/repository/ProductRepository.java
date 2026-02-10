package com.example.eshop.repository;

import com.example.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {

    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public boolean deleteProduct(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }
        return productData.removeIf(p -> id.equals(p.getProductId()));
    }

    public Product findById(String productId){
        if (productId == null || productId.isBlank()) {
            return null;
        }

        return productData.stream()
                .filter(p -> productId.equals(p.getProductId()))
                .findFirst()
                .orElse(null);
    }

    public boolean update(Product product) {
        if (product == null || product.getProductId() == null
                || product.getProductId().isBlank()) {
            return false;
        }

        for (Product current : productData) {
            if (product.getProductId().equals(current.getProductId())) {
                applyUpdate(current, product);
                return true;
            }
        }

        return false;
    }

    private void applyUpdate(Product target, Product source) {
        target.setProductName(source.getProductName());
        target.setProductQuantity(source.getProductQuantity());
    }

}
