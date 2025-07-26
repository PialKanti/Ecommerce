package com.example.ecommerce_service.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageConstants {
    public static final String WISHLIST_ITEMS_RETRIEVED = "Wishlist items retrieved successfully.";
    public static final String DAILY_SALES_AMOUNT_FETCH_SUCCESS = "Daily sales amount fetched successfully";
    public static final String MAX_SALE_DAY_FETCH_SUCCESS = "Maximum sale day fetched successfully";
    public static final String TOP_5_PRODUCTS_BY_TOTAL_SALES_AMOUNT_SUCCESS = "Top 5 products of all time by total sales amount retrieved successfully";
    public static final String LAST_MONTH_TOP_5_PRODUCTS_BY_COUNT_SUCCESS = "Last month's top 5 products by sales number retrieved successfully";
}