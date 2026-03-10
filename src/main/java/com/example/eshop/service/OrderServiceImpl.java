package com.example.eshop.service;

import com.example.eshop.model.Order;
import com.example.eshop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        if (orderRepository.findById(order.getId()) != null) {
            throw new IllegalArgumentException("Order with id " + order.getId() + " already exists");
        }
        return orderRepository.save(order);
    }

    @Override
    public Order updateStatus(String orderId, String status) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new NoSuchElementException();
        }
        Order newOrder = new Order(
                order.getId(),
                order.getProducts(),
                order.getOrderTime(),
                order.getAuthor(),
                status
        );
        return orderRepository.save(newOrder);
    }

    @Override
    public Order findById(String orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List<Order> findAllByAuthor(String author) {
        return orderRepository.findAllByAuthor(author);
    }
}