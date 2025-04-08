package com.kirtesh.ecommerce.payment;


import com.kirtesh.ecommerce.order.PaymentMethod;
import com.kirtesh.ecommerce.user.UserResponse;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        UserResponse user
) {
}
