package com.example.eshop;

import com.example.eshop.model.Order;
import com.example.eshop.model.Payment;
import com.example.eshop.model.Product;
import com.example.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

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
    }

    @Test
    void testAddPaymentVoucherSuccess() {

        paymentData.put("voucherCode","ESHOP1234ABC5678");

        Payment payment = paymentService.addPayment(order,"VOUCHER",paymentData);

        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("SUCCESS", order.getStatus());

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentVoucherRejected() {

        paymentData.put("voucherCode","INVALID");

        Payment payment = paymentService.addPayment(order,"VOUCHER",paymentData);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", order.getStatus());

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentCODSuccess() {

        paymentData.put("address","Jakarta");
        paymentData.put("deliveryFee","10000");

        Payment payment = paymentService.addPayment(order,"COD",paymentData);

        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testAddPaymentCODRejected() {

        paymentData.put("address","");
        paymentData.put("deliveryFee","");

        Payment payment = paymentService.addPayment(order,"COD",paymentData);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void testAddPaymentBankTransferSuccess() {

        paymentData.put("bankName","BCA");
        paymentData.put("referenceCode","TRX123456");

        Payment payment = paymentService.addPayment(order,"BANK_TRANSFER",paymentData);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testAddPaymentBankTransferRejected() {

        paymentData.put("bankName","");
        paymentData.put("referenceCode","");

        Payment payment = paymentService.addPayment(order,"BANK_TRANSFER",paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testGetPayment() {

        Payment payment = new Payment(order,"VOUCHER",paymentData);

        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());

        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetAllPayments() {

        List<Payment> payments = new ArrayList<>();

        payments.add(new Payment(order,"VOUCHER",paymentData));
        payments.add(new Payment(order,"COD",paymentData));

        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2,result.size());
    }


}