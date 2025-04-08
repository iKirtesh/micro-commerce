package com.kirtesh.ecommerce.user;

public record UserResponse (
        Integer id,
        String firstName,
        String lastName,
        String email,
        Address address) {}
