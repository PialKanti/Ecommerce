package com.example.ecommerce_service.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MaxSaleDayResponse(LocalDate orderDate,
                                 Double totalSalesAmount,
                                 String currency) {
}
