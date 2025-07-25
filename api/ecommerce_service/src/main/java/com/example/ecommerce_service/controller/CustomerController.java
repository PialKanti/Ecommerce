package com.example.ecommerce_service.controller;

import com.example.ecommerce_service.constant.ApiEndpointConstant;
import com.example.ecommerce_service.dto.response.PaginatedApiResponse;
import com.example.ecommerce_service.projection.WishlistItemProjection;
import com.example.ecommerce_service.service.CustomerService;
import com.example.ecommerce_service.validator.QueryParamValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiEndpointConstant.Customer.BASE)
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping(ApiEndpointConstant.Customer.WISHLIST)
    public ResponseEntity<PaginatedApiResponse<WishlistItemProjection>> findWishlistByCustomerId(@PathVariable Long customerId,
                                                                                                 @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                                                 @RequestParam(name = "page_size", defaultValue = "5", required = false) int pageSize) {
        QueryParamValidator.validatePageRequest(page, pageSize);

        Pageable pageable = PageRequest.of(page, pageSize);
        return ResponseEntity.ok(customerService.findWishlistItemsByCustomerId(customerId, pageable));
    }
}
