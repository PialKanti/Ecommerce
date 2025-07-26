package com.example.ecommerce_service.service;

import com.example.ecommerce_service.dto.response.PaginatedApiResponse;
import com.example.ecommerce_service.projection.WishlistItemProjection;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    PaginatedApiResponse<WishlistItemProjection> findWishlistItemsByCustomerId(Long customerId, Pageable pageable);
}
