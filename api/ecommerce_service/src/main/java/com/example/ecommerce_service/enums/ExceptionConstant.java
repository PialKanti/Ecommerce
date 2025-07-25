package com.example.ecommerce_service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionConstant {
    CUSTOMER_NOT_FOUND("Customer not found");

    private final String message;
}
