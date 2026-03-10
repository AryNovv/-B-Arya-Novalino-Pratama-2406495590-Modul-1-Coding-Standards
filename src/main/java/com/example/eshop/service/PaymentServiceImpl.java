package com.example.eshop.service;

import com.example.eshop.model.Order;
import com.example.eshop.model.Payment;
import com.example.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {

        Payment payment = new Payment(order, method, paymentData);

        if (method.equals("VOUCHER")) {
            validateVoucher(payment, paymentData.get("voucherCode"));
        }

        if (method.equals("COD")) {
            validateCOD(payment, paymentData);
        }

        if (method.equals("BANK_TRANSFER")) {
            validateBankTransfer(payment, paymentData);
        }

        paymentRepository.save(payment);
        updateOrderStatus(payment);

        return payment;
    }

    private void validateVoucher(Payment payment, String voucher) {

        if (voucher != null &&
                voucher.length() == 16 &&
                voucher.startsWith("ESHOP") &&
                voucher.replaceAll("[^0-9]", "").length() == 8) {

            payment.setStatus("SUCCESS");

        } else {
            payment.setStatus("REJECTED");
        }
    }

    private void validateCOD(Payment payment, Map<String,String> data) {

        String address = data.get("address");
        String fee = data.get("deliveryFee");

        if (address == null || address.isEmpty() || fee == null || fee.isEmpty()) {
            payment.setStatus("REJECTED");
        } else {
            payment.setStatus("SUCCESS");
        }
    }

    private void validateBankTransfer(Payment payment, Map<String,String> data) {

        String bank = data.get("bankName");
        String reference = data.get("referenceCode");

        if (bank == null || bank.isEmpty() || reference == null || reference.isEmpty()) {
            payment.setStatus("REJECTED");
        } else {
            payment.setStatus("SUCCESS");
        }
    }

    private void updateOrderStatus(Payment payment) {

        if (payment.getStatus().equals("SUCCESS")) {
            payment.getOrder().setStatus("SUCCESS");
        }

        if (payment.getStatus().equals("REJECTED")) {
            payment.getOrder().setStatus("FAILED");
        }
    }

    @Override
    public Payment setStatus(Payment payment, String status) {

        payment.setStatus(status);
        updateOrderStatus(payment);

        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

}