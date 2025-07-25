package com.example.ecommerce_service.projection;

import java.time.LocalDate;

public interface MaxSalesDayProjection {
    LocalDate getOrderDate();
    Double getTotalSalesAmount();
}
