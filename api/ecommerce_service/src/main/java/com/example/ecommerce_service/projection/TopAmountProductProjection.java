package com.example.ecommerce_service.projection;

public interface TopAmountProductProjection {
    Long getId();
    String getName();
    String getDescription();
    Double getTotalSalesAmount();
}
