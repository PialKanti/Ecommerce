package com.example.ecommerce_service.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record PaginatedApiResponse<T>(String message,
                                      int page,
                                      int pageSize,
                                      long totalItems,
                                      int totalPages,
                                      List<T> data) {
    public static <T> PaginatedApiResponse<T> success(String message, Page<T> page) {
        return new PaginatedApiResponse<>(
                message,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getContent()
        );
    }
}
