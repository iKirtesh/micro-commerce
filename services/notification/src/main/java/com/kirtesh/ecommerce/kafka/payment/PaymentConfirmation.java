package com.kirtesh.ecommerce.kafka.payment;

import java.math.BigDecimal;

public record PaymentConfirmation(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String userFirstname,
        String userLastname,
        String userEmail
) {
}
