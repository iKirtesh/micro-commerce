package com.kirtesh.ecommerce.handler;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class ErrorResponse {
    Map<String, String> errors;
}
