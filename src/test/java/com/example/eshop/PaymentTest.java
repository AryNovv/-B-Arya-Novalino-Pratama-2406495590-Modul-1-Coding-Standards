package com.example.eshop;

import com.example.eshop.model.Order;
import com.example.eshop.model.Payment;
import com.example.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    Order order;
    Map<String,String> paymentData;

    @BeforeEach
    void setUp() {

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
        paymentData.put("voucherCode","ESHOP1234ABC5678");
    }

    @Test
    void testCreatePayment() {

        Payment payment = new Payment(order,"VOUCHER",paymentData);

        assertEquals(order,payment.getOrder());
        assertEquals("VOUCHER",payment.getMethod());
        assertEquals(paymentData,payment.getPaymentData());

        assertNotNull(payment.getId());
    }
}
