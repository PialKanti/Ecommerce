package com.example.ecommerce_service.service;

import com.example.ecommerce_service.dto.response.ApiResponse;
import com.example.ecommerce_service.dto.response.DailySalesAmountResponse;

import java.time.LocalDate;

public interface OrderService {
    ApiResponse<DailySalesAmountResponse> getDailySalesTotalByOrderDate(LocalDate date);
}
