package com.example.ecommerce_service.controller;

import com.example.ecommerce_service.constant.ApiEndpointConstant;
import com.example.ecommerce_service.dto.response.ApiResponse;
import com.example.ecommerce_service.dto.response.DailySalesAmountResponse;
import com.example.ecommerce_service.dto.response.MaxSaleDayResponse;
import com.example.ecommerce_service.dto.response.PaginatedApiResponse;
import com.example.ecommerce_service.service.OrderService;
import com.example.ecommerce_service.validator.QueryParamValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(ApiEndpointConstant.SalesAnalytics.MAX_SALE_DAY)
    public ResponseEntity<PaginatedApiResponse<MaxSaleDayResponse>> findMaxSaleDays(@RequestParam LocalDate startDate,
                                                                                    @RequestParam LocalDate endDate,
                                                                                    @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                                    @RequestParam(name = "page_size", defaultValue = "5", required = false) int pageSize) {

        QueryParamValidator.validateDateRange(startDate, endDate);
        QueryParamValidator.validatePageRequest(page, pageSize);

        Pageable pageable = PageRequest.of(page, pageSize);
        return ResponseEntity.ok(orderService.findMaxSaleDays(startDate, endDate, pageable));
    }
}
