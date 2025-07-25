package com.example.ecommerce_service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionConstant {
    CUSTOMER_NOT_FOUND("Customer not found"),
    START_DATE_AND_END_DATE_MANDATORY("Start date and end date must be provided"),
    START_DATE_AFTER_END_DATE("Start date must not be after end date"),
    PAGE_INDEX_NEGATIVE("Page index must be greater than or equal to 0"),
    PAGE_SIZE_NEGATIVE("Page size must be greater than 0");

    private final String message;
}
