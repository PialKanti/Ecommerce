package com.example.ecommerce_service.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageConstants {
    public static final String WISHLIST_ITEMS_RETRIEVED = "Wishlist items retrieved successfully.";
    public static final String DAILY_SALES_AMOUNT_FETCH_SUCCESS = "Daily sales amount fetched successfully";
    public static final String MAX_SALE_DAY_FETCH_SUCCESS = "Maximum sale day fetched successfully";
    public static final String TOP_SELLING_PRODUCTS_BY_AMOUNT_FETCH_SUCCESS = "Top 5 selling products by sales amount retrieved successfully";
}