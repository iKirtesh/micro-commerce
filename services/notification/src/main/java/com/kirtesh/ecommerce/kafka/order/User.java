package com.kirtesh.ecommerce.kafka.order;

public record User (
        Integer id,
        String firstname,
        String lastname,
        String email
){
}
