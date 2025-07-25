package com.example.ecommerce_service.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record PaginatedApiResponse<T>(String message,
                                      int page,
                                      int pageSize,
                                      long totalItems,
                                      int totalPages,
                                      List<T> data) {
    public static <T> PaginatedApiResponse<T> success(String message, Page<T> page) {
        return PaginatedApiResponse.<T>builder()
                .message(message)
                .page(page.getNumber())
                .pageSize(page.getSize())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .data(page.getContent())
                .build();
    }
}
