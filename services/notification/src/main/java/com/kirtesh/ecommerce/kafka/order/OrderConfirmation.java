package com.kirtesh.ecommerce.kafka.order;

import com.kirtesh.ecommerce.kafka.payment.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        User user,
        List<Product> products) {
}
