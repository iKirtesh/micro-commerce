package com.kirtesh.ecommerce.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kirtesh.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record OrderRequest(
        Integer id,
        String reference,

        @Positive(message = "Total amount should be positive")
        BigDecimal totalAmount,

        @NotNull(message = "Payment method should be precised")
        PaymentMethod paymentMethod,

        @NotNull(message = "User id should be present")
        Integer userId,

        @NotEmpty(message = "you should purchase at least one product")
        List<PurchaseRequest> products
) {
}
