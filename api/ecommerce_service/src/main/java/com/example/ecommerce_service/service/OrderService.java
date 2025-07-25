package com.example.ecommerce_service.service;

import com.example.ecommerce_service.dto.response.ApiResponse;
import com.example.ecommerce_service.dto.response.DailySalesAmountResponse;
import com.example.ecommerce_service.dto.response.MaxSaleDayResponse;
import com.example.ecommerce_service.dto.response.PaginatedApiResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface OrderService {
    ApiResponse<DailySalesAmountResponse> getDailySalesTotalByOrderDate(LocalDate date);
    PaginatedApiResponse<MaxSaleDayResponse> findMaxSaleDays(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
