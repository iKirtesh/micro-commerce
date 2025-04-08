package com.kirtesh.ecommerce.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UserRequest(Integer id,

        @NotNull(message = "First name is required")
        String firstName,

        @NotNull(message = "Last name is required")
        String lastName,

        @NotNull(message = "Email is required")
        String email,

        @Valid
        @NotNull(message = "Address is required")
        Address address
) {}
