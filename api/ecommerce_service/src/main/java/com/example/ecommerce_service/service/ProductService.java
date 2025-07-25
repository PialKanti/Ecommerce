package com.example.ecommerce_service.service;

import com.example.ecommerce_service.dto.response.ApiResponse;
import com.example.ecommerce_service.dto.response.TopAmountProductResponse;
import com.example.ecommerce_service.dto.response.TopCountProductResponse;

import java.util.List;

public interface ProductService {
    ApiResponse<List<TopAmountProductResponse>> findTopSellingProductsBySalesAmount();
    ApiResponse<List<TopCountProductResponse>> findTopSellingProductsBySalesCount();
}
