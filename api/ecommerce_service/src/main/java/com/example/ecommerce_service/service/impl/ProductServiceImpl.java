package com.example.ecommerce_service.service.impl;

import com.example.ecommerce_service.dto.response.ApiResponse;
import com.example.ecommerce_service.dto.response.TopAmountProductResponse;
import com.example.ecommerce_service.dto.response.TopCountProductResponse;
import com.example.ecommerce_service.projection.TopAmountProductProjection;
import com.example.ecommerce_service.projection.TopCountProductProjection;
import com.example.ecommerce_service.repository.ProductRepository;
import com.example.ecommerce_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static com.example.ecommerce_service.constant.MessageConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ApiResponse<List<TopAmountProductResponse>> findTopSellingProductsBySalesAmount() {
        Pageable pageable = PageRequest.of(0, 5);

        Page<TopAmountProductProjection> pageResponse = productRepository.findTopSellingProductsBySalesAmount(pageable);

        log.info("Found {} products for top selling by amount", pageResponse.getTotalElements());

        List<TopAmountProductResponse> topSellingProducts = pageResponse.getContent().stream()
                .map(projection -> TopAmountProductResponse.builder()
                        .id(projection.getId())
                        .name(projection.getName())
                        .description(projection.getDescription())
                        .totalSalesAmount(projection.getTotalSalesAmount())
                        .build())
                .toList();

        return ApiResponse.success(TOP_5_PRODUCTS_BY_TOTAL_SALES_AMOUNT_SUCCESS, topSellingProducts);
    }

    @Override
    public ApiResponse<List<TopCountProductResponse>> findTopSellingProductsBySalesCount() {
        Pageable pageable = PageRequest.of(0, 5);

        YearMonth previousMonth = getPreviousMonth();
        LocalDate startDate = previousMonth.atDay(1);
        LocalDate endDate = previousMonth.atEndOfMonth();

        Page<TopCountProductProjection> pageResponse = productRepository.findTopSellingProductsBySalesCount(startDate, endDate, pageable);

        log.info("Found {} products for top selling by count last month", pageResponse.getTotalElements());

        List<TopCountProductResponse> topSellingProducts = pageResponse.getContent().stream()
                .map(projection -> TopCountProductResponse.builder()
                        .id(projection.getId())
                        .name(projection.getName())
                        .description(projection.getDescription())
                        .totalSalesCount(projection.getTotalSalesCount())
                        .build())
                .toList();

        return ApiResponse.success(LAST_MONTH_TOP_5_PRODUCTS_BY_COUNT_SUCCESS, topSellingProducts);
    }

    private YearMonth getPreviousMonth() {
        LocalDate today = LocalDate.now();
        return YearMonth.from(today.minusMonths(1));
    }
}
