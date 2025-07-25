package com.example.ecommerce_service.service.impl;

import com.example.ecommerce_service.config.ApplicationConfig;
import com.example.ecommerce_service.dto.response.ApiResponse;
import com.example.ecommerce_service.dto.response.DailySalesAmountResponse;
import com.example.ecommerce_service.repository.OrderRepository;
import com.example.ecommerce_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.example.ecommerce_service.constant.MessageConstants.DAILY_SALES_AMOUNT_FETCH_SUCCESS;

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
}
