package com.example.ecommerce_service.controller;

import com.example.ecommerce_service.constant.ApiEndpointConstant;
import com.example.ecommerce_service.dto.response.PaginatedApiResponse;
import com.example.ecommerce_service.projection.WishlistItemProjection;
import com.example.ecommerce_service.service.CustomerService;
import com.example.ecommerce_service.validator.QueryParamValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiEndpointConstant.Customer.BASE)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Customer", description = "Operations related to customer information and activities")
public class CustomerController {
    private final CustomerService customerService;

    @Operation(
            summary = "Get customer's wishlist",
            description = "Returns a paginated list of items in the customer's wishlist based on the customer ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved wishlist items",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaginatedApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping(ApiEndpointConstant.Customer.WISHLIST)
    public ResponseEntity<PaginatedApiResponse<WishlistItemProjection>> findWishlistByCustomerId(@Parameter(description = "ID of the customer whose wishlist is to be retrieved", example = "1", required = true)
                                                                                                 @PathVariable Long customerId,
                                                                                                 @Parameter(description = "Page number for pagination (zero-based)", example = "0")
                                                                                                 @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                                                 @Parameter(description = "Number of items per page", example = "5")
                                                                                                 @RequestParam(name = "page_size", defaultValue = "5", required = false) int pageSize) {

        log.info("Finding wishlist items by customer ID = {}, Page = {}, Page Size = {}", customerId, page, pageSize);

        QueryParamValidator.validatePageRequest(page, pageSize);
        Pageable pageable = PageRequest.of(page, pageSize);

        return ResponseEntity.ok(customerService.findWishlistItemsByCustomerId(customerId, pageable));
    }
}
