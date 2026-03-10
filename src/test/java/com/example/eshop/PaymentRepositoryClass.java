package com.example.eshop;

import com.example.eshop.model.Order;
import com.example.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;

    Order order;
    Map<String,String> paymentData;

    @BeforeEach
    void setUp() {

        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);

        products.add(product);

        order = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                products,
                1708560000L,
                "Safira Sudrajat"
        );

        paymentData = new HashMap<>();
    }

    @Test
    void testSavePayment() {

        Payment payment = new Payment(order,"VOUCHER",paymentData);

        Payment result = paymentRepository.save(payment);

        assertEquals(payment.getId(),result.getId());
    }
    @Test
    void testFindByIdIfFound() {

        Payment payment = new Payment(order,"VOUCHER",paymentData);

        paymentRepository.save(payment);

        Payment result = paymentRepository.findById(payment.getId());

        assertEquals(payment.getId(),result.getId());
    }
    @Test
    void testFindByIdIfNotFound() {

        Payment result = paymentRepository.findById("unknown");

        assertNull(result);
    }
    @Test
    void testFindAllPayments() {

        Payment p1 = new Payment(order,"VOUCHER",paymentData);
        Payment p2 = new Payment(order,"COD",paymentData);

        paymentRepository.save(p1);
        paymentRepository.save(p2);

        List<Payment> results = paymentRepository.findAll();

        assertEquals(2,results.size());
    }
}