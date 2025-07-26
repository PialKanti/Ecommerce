package com.example.ecommerce_service.service.impl;

import com.example.ecommerce_service.dto.response.PaginatedApiResponse;
import com.example.ecommerce_service.enums.ExceptionConstant;
import com.example.ecommerce_service.projection.WishlistItemProjection;
import com.example.ecommerce_service.repository.CustomerRepository;
import com.example.ecommerce_service.repository.WishlistRepository;
import com.example.ecommerce_service.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.ecommerce_service.constant.MessageConstants.WISHLIST_ITEMS_RETRIEVED;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final WishlistRepository wishlistRepository;

    @Override
    public PaginatedApiResponse<WishlistItemProjection> findWishlistItemsByCustomerId(Long customerId, Pageable pageable) {
        if (!customerRepository.existsById(customerId)) {
            log.warn("Customer id {} does not exist", customerId);
            throw new EntityNotFoundException(ExceptionConstant.CUSTOMER_NOT_FOUND.getMessage());
        }

        Page<WishlistItemProjection> pageResponse = wishlistRepository.findWishlistItemsByCustomerId(customerId, pageable);

        log.info("Found total {} wishlist items for customerId = {}", pageResponse.getTotalElements(), customerId);

        return PaginatedApiResponse.success(WISHLIST_ITEMS_RETRIEVED, pageResponse);
    }
}
