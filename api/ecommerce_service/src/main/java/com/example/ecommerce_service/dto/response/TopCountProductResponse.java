package com.example.ecommerce_service.dto.response;

import lombok.Builder;

@Builder
public record TopCountProductResponse(Long id,
                                      String name,
                                      String description,
                                      Integer totalSalesCount) {
}
