package com.example.ecommerce_service.controller;

import com.example.ecommerce_service.constant.ApiEndpointConstant;
import com.example.ecommerce_service.dto.response.ApiResponse;
import com.example.ecommerce_service.dto.response.DailySalesAmountResponse;
import com.example.ecommerce_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(ApiEndpointConstant.SalesAnalytics.BASE)
@RequiredArgsConstructor
public class SalesAnalyticsController {
    private final OrderService orderService;

    @GetMapping(ApiEndpointConstant.SalesAnalytics.TODAY_TOTAL_AMOUNT)
    public ResponseEntity<ApiResponse<DailySalesAmountResponse>> findTotalSalesAmountForToday() {
        return ResponseEntity.ok(orderService.getDailySalesTotalByOrderDate(LocalDate.now()));
    }

}
