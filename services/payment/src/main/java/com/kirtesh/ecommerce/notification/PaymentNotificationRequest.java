package com.kirtesh.ecommerce.notification;

import com.kirtesh.ecommerce.payment.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String userFirstname,
        String userLastname,
        String userEmail
) {
}