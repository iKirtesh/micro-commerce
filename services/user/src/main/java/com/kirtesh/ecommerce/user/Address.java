package com.kirtesh.ecommerce.user;


import jakarta.persistence.Embeddable;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Embeddable
@Data
@NoArgsConstructor
@Validated
public class Address {
    private String street;
    private String houseNumber;
    private String zipCode;
}
