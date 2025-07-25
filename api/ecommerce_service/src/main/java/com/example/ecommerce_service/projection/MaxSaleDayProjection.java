package com.example.ecommerce_service.projection;

import java.time.LocalDate;

public interface MaxSaleDayProjection {
    LocalDate getOrderDate();
    Double getTotalSalesAmount();
}
