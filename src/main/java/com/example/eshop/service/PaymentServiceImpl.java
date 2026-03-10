package com.example.eshop.service;

import com.example.eshop.enums.OrderStatus;
import com.example.eshop.enums.PaymentMethod;
import com.example.eshop.enums.PaymentStatus;
import com.example.eshop.model.Order;
import com.example.eshop.model.Payment;
import com.example.eshop.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(order, method, paymentData);

        if (method.equals(PaymentMethod.VOUCHER.getValue())) {
            validateVoucher(payment, paymentData.get("voucherCode"));
        } else if (method.equals(PaymentMethod.COD.getValue())) {
            validateCOD(payment, paymentData);
        } else if (method.equals(PaymentMethod.BANK_TRANSFER.getValue())) {
            validateBankTransfer(payment, paymentData);
        } else {
            payment.setStatus(PaymentStatus.REJECTED.getValue());
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
            payment.setStatus(PaymentStatus.SUCCESS.getValue());
        } else {
            payment.setStatus(PaymentStatus.REJECTED.getValue());
        }
    }

    private void validateCOD(Payment payment, Map<String, String> data) {
        String address = data.get("address");
        String fee = data.get("deliveryFee");

        if (address == null || address.isEmpty() || fee == null || fee.isEmpty()) {
            payment.setStatus(PaymentStatus.REJECTED.getValue());
        } else {
            payment.setStatus(PaymentStatus.WAITING.getValue());
        }
    }

    private void validateBankTransfer(Payment payment, Map<String, String> data) {
        String bank = data.get("bankName");
        String reference = data.get("referenceCode");

        if (bank == null || bank.isEmpty() || reference == null || reference.isEmpty()) {
            payment.setStatus(PaymentStatus.REJECTED.getValue());
        } else {
            payment.setStatus(PaymentStatus.SUCCESS.getValue());
        }
    }

    private void updateOrderStatus(Payment payment) {
        if (PaymentStatus.SUCCESS.getValue().equals(payment.getStatus())) {
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        } else if (PaymentStatus.REJECTED.getValue().equals(payment.getStatus())) {
            payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        }
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        updateOrderStatus(payment);
        paymentRepository.save(payment);
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