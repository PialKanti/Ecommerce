package com.example.ecommerce_service.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtil {
    public static YearMonth getPreviousMonth() {
        LocalDate today = LocalDate.now();
        return YearMonth.from(today.minusMonths(1));
    }
}
