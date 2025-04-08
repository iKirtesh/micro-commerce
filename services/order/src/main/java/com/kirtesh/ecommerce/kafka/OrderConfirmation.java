package com.kirtesh.ecommerce.kafka;


import com.kirtesh.ecommerce.order.PaymentMethod;
import com.kirtesh.ecommerce.product.PurchaseResponse;
import com.kirtesh.ecommerce.user.UserResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        UserResponse user,
        List<PurchaseResponse> products
) {
}
