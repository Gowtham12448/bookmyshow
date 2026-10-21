package com.bookmyshow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Placeholder controller for future payment integrations (Razorpay/Stripe).
 * The actual payment flow currently lives in BookingController via:
 *   POST /payment  →  POST /confirm-booking  →  GET /ticket/{id}
 *
 * When integrating a real gateway, add endpoints here:
 *   - POST /api/payment/create-order  → returns gateway order id
 *   - POST /api/payment/verify        → verifies webhook signature
 */
@Controller
public class PaymentController {

    @GetMapping("/payment-gateway-info")
    public String gatewayInfo() {
        // Simple landing redirect — keeps endpoint useful without breaking nav
        return "redirect:/home";
    }
}