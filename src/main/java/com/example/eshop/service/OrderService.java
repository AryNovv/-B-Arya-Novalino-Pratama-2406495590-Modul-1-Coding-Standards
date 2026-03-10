package com.example.eshop.service;

import com.example.eshop.model.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);

    Order updateStatus(String orderId, String status);

    Order findById(String orderId);

    List<Order> findAllByAuthor(String author);
}