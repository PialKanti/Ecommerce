package com.example.ecommerce_service.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiEndpointConstant {

    public static final String API_VERSION = "/api/v1";

    public static final class Customer {
        /**
         * Base endpoint for customer APIs
         */
        public static final String BASE = API_VERSION + "/customers";
        /**
         * Endpoint for customer's wishlist (relative path)
         */
        public static final String WISHLIST = "/{customerId}/wishlist";
    }

    public static final class SalesAnalytics {
        /**
         * Base endpoint for sales analytics APIs
         */
        public static final String BASE = API_VERSION + "/analytics/sales";

        /**
         * Endpoint to get total sales amount for today
         */
        public static final String TODAY_TOTAL_AMOUNT = "/today/total";
    }
}
