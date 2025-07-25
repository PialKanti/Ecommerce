package com.example.ecommerce_service.controller;

import com.example.ecommerce_service.constant.ApiEndpointConstant;
import com.example.ecommerce_service.dto.response.ApiResponse;
import com.example.ecommerce_service.dto.response.TopAmountProductResponse;
import com.example.ecommerce_service.dto.response.TopCountProductResponse;
import com.example.ecommerce_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiEndpointConstant.ProductAnalytics.BASE)
@RequiredArgsConstructor
public class ProductAnalyticsController {
    private final ProductService productService;

    @GetMapping(ApiEndpointConstant.ProductAnalytics.TOP_ALL_TIME_BY_AMOUNT)
    public ResponseEntity<ApiResponse<List<TopAmountProductResponse>>> findTopSellingByAmount() {
        return ResponseEntity.ok(productService.findTopSellingProductsBySalesAmount());
    }

    @GetMapping(ApiEndpointConstant.ProductAnalytics.TOP_LAST_MONTH_BY_COUNT)
    public ResponseEntity<ApiResponse<List<TopCountProductResponse>>> findTopSellingByCount() {
        return ResponseEntity.ok(productService.findTopSellingProductsBySalesCount());
    }
}
