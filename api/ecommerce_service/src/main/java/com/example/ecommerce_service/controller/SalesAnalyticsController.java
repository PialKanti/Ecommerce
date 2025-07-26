package com.example.ecommerce_service.controller;

import com.example.ecommerce_service.constant.ApiEndpointConstant;
import com.example.ecommerce_service.dto.response.ApiResponse;
import com.example.ecommerce_service.dto.response.DailySalesAmountResponse;
import com.example.ecommerce_service.dto.response.MaxSaleDayResponse;
import com.example.ecommerce_service.dto.response.PaginatedApiResponse;
import com.example.ecommerce_service.service.OrderService;
import com.example.ecommerce_service.validator.QueryParamValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(ApiEndpointConstant.SalesAnalytics.BASE)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Sales Analytics", description = "Endpoints for retrieving sales analytics data")
public class SalesAnalyticsController {
    private final OrderService orderService;

    @Operation(
            summary = "Get today's total sales amount",
            description = "Returns the total sales amount for the current day"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved today's total sales amount",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DailySalesAmountResponse.class)))
    })
    @GetMapping(ApiEndpointConstant.SalesAnalytics.TODAY_TOTAL_AMOUNT)
    public ResponseEntity<ApiResponse<DailySalesAmountResponse>> findTotalSalesAmountForToday() {
        log.info("Received request to fetch today's total sales amount");
        return ResponseEntity.ok(orderService.getDailySalesTotalByOrderDate(LocalDate.now()));
    }

    @Operation(
            summary = "Get max sale day(s) within a date range",
            description = "Returns the day(s) with the highest total sales amount between the given start and end dates"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved max sale day(s)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MaxSaleDayResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid date range or pagination parameters",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping(ApiEndpointConstant.SalesAnalytics.MAX_SALE_DAY)
    public ResponseEntity<PaginatedApiResponse<MaxSaleDayResponse>> findMaxSaleDays(@Parameter(description = "Start date of the range (inclusive)", example = "2025-07-01", required = true)
                                                                                    @RequestParam LocalDate startDate,
                                                                                    @Parameter(description = "End date of the range (inclusive)", example = "2025-07-26", required = true)
                                                                                    @RequestParam LocalDate endDate,
                                                                                    @Parameter(description = "Page number for pagination (zero-based)", example = "0")
                                                                                    @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                                    @Parameter(description = "Number of items per page", example = "5")
                                                                                    @RequestParam(name = "page_size", defaultValue = "5", required = false) int pageSize) {

        log.info("Received request to fetch max sale days between {} and {}. Page: {}, Page Size: {}",
                startDate, endDate, page, pageSize);

        QueryParamValidator.validateDateRange(startDate, endDate);
        QueryParamValidator.validatePageRequest(page, pageSize);

        Pageable pageable = PageRequest.of(page, pageSize);
        return ResponseEntity.ok(orderService.findMaxSaleDays(startDate, endDate, pageable));
    }
}
