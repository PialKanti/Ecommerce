package com.example.ecommerce_service.service.impl;

import com.example.ecommerce_service.dto.response.ApiResponse;
import com.example.ecommerce_service.dto.response.TopAmountProductResponse;
import com.example.ecommerce_service.projection.TopAmountProductProjection;
import com.example.ecommerce_service.repository.ProductRepository;
import com.example.ecommerce_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.ecommerce_service.constant.MessageConstants.TOP_SELLING_PRODUCTS_BY_AMOUNT_FETCH_SUCCESS;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ApiResponse<List<TopAmountProductResponse>> findTopSellingProductsBySalesAmount() {
        Pageable pageable = PageRequest.of(0, 5);

        Page<TopAmountProductProjection> pageResponse = productRepository.findTopSellingProductsBySalesAmount(pageable);

        List<TopAmountProductResponse> topSellingProducts = pageResponse.getContent().stream()
                .map(projection -> TopAmountProductResponse.builder()
                        .id(projection.getId())
                        .name(projection.getName())
                        .description(projection.getDescription())
                        .totalSalesAmount(projection.getTotalSalesAmount())
                        .build())
                .toList();

        return ApiResponse.success(TOP_SELLING_PRODUCTS_BY_AMOUNT_FETCH_SUCCESS, topSellingProducts);
    }
}
