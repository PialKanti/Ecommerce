package com.example.ecommerce_service.dto.response;

import lombok.Builder;

@Builder
public record TopAmountProductResponse(Long id,
                                       String name,
                                       String description,
                                       Double totalSalesAmount) {
}
