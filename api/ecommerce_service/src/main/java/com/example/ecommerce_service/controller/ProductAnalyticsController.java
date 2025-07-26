package com.example.ecommerce_service.controller;

import com.example.ecommerce_service.constant.ApiEndpointConstant;
import com.example.ecommerce_service.dto.response.ApiResponse;
import com.example.ecommerce_service.dto.response.TopAmountProductResponse;
import com.example.ecommerce_service.dto.response.TopCountProductResponse;
import com.example.ecommerce_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiEndpointConstant.ProductAnalytics.BASE)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Product Analytics", description = "Endpoints for analyzing product sales performance")
public class ProductAnalyticsController {
    private final ProductService productService;

    @Operation(
            summary = "Get top 5 selling products of all time by total sales amount",
            description = "Returns the top 5 products that have generated the highest total revenue across all time"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved top-selling products by amount",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TopAmountProductResponse.class)))
    })
    @GetMapping(ApiEndpointConstant.ProductAnalytics.TOP_ALL_TIME_BY_AMOUNT)
    public ResponseEntity<ApiResponse<List<TopAmountProductResponse>>> findTopSellingByAmount() {
        log.info("Fetching top 5 selling products of all time by sales amount");
        return ResponseEntity.ok(productService.findTopSellingProductsBySalesAmount());
    }

    @Operation(
            summary = "Get top 5 selling products of the last month by sales count",
            description = "Returns the top 5 products with the highest number of sales in the previous calendar month"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved top-selling products by count",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TopCountProductResponse.class)))
    })
    @GetMapping(ApiEndpointConstant.ProductAnalytics.TOP_LAST_MONTH_BY_COUNT)
    public ResponseEntity<ApiResponse<List<TopCountProductResponse>>> findTopSellingByCount() {
        log.info("Fetching top 5 selling products of last month by sales count");
        return ResponseEntity.ok(productService.findTopSellingProductsBySalesCount());
    }
}
