package com.bookmyshow.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    /** Generate a unique transaction ID for mock payment confirmation. */
    public String generateTransactionId() {
        return "TXN" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }

    /**
     * In production, this method would call the payment gateway (Razorpay/Stripe).
     * For this demo, it always returns SUCCESS.
     */
    public boolean processPayment(String paymentMethod, double amount) {
        return paymentMethod != null && amount > 0;
    }
}