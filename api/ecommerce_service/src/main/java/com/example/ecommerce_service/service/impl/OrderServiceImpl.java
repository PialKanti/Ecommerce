package com.example.ecommerce_service.service.impl;

import com.example.ecommerce_service.config.ApplicationConfig;
import com.example.ecommerce_service.dto.response.ApiResponse;
import com.example.ecommerce_service.dto.response.DailySalesAmountResponse;
import com.example.ecommerce_service.dto.response.MaxSaleDayResponse;
import com.example.ecommerce_service.dto.response.PaginatedApiResponse;
import com.example.ecommerce_service.projection.MaxSaleDayProjection;
import com.example.ecommerce_service.repository.OrderRepository;
import com.example.ecommerce_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.example.ecommerce_service.constant.MessageConstants.DAILY_SALES_AMOUNT_FETCH_SUCCESS;
import static com.example.ecommerce_service.constant.MessageConstants.MAX_SALE_DAY_FETCH_SUCCESS;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ApplicationConfig applicationConfig;

    @Override
    public ApiResponse<DailySalesAmountResponse> getDailySalesTotalByOrderDate(LocalDate date) {
        Double dailySalesTotal = orderRepository.getTotalSalesAmountByOrderDate(date);

        return ApiResponse.success(DAILY_SALES_AMOUNT_FETCH_SUCCESS, DailySalesAmountResponse.builder()
                .date(date)
                .totalSalesAmount(dailySalesTotal)
                .currency(applicationConfig.getCurrency())
                .build());
    }

    @Override
    public PaginatedApiResponse<MaxSaleDayResponse> findMaxSaleDays(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<MaxSaleDayProjection> response = orderRepository.findMaxSalesDayByOrderDateRange(startDate, endDate, pageable);

        Page<MaxSaleDayResponse> maxSaleDayResponse = response.map(projection -> MaxSaleDayResponse.builder()
                .orderDate(projection.getOrderDate())
                .totalSalesAmount(projection.getTotalSalesAmount())
                .currency(applicationConfig.getCurrency())
                .build());

        return PaginatedApiResponse.success(MAX_SALE_DAY_FETCH_SUCCESS, maxSaleDayResponse);
    }
}
