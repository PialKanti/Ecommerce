package com.example.ecommerce_service.service.impl;

import com.example.ecommerce_service.dto.response.PaginatedApiResponse;
import com.example.ecommerce_service.projection.WishlistItemProjection;
import com.example.ecommerce_service.repository.WishlistRepository;
import com.example.ecommerce_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.ecommerce_service.constant.MessageConstants.WISHLIST_ITEMS_RETRIEVED;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final WishlistRepository wishlistRepository;

    @Override
    public PaginatedApiResponse<WishlistItemProjection> findWishlistItemsByCustomerId(Long customerId, Pageable pageable) {
        Page<WishlistItemProjection> pageResponse = wishlistRepository.findWishlistItemsByCustomerId(customerId, pageable);

        return PaginatedApiResponse.success(WISHLIST_ITEMS_RETRIEVED, pageResponse);
    }
}
