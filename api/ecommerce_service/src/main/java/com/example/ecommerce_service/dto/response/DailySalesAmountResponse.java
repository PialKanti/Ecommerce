package com.example.ecommerce_service.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DailySalesAmountResponse(LocalDate date,
                                       Double totalSalesAmount,
                                       String currency) {
}
