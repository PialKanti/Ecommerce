package com.example.ecommerce_service.validator;

import com.example.ecommerce_service.exception.InvalidQueryParamException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.example.ecommerce_service.enums.ExceptionConstant.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QueryParamValidator {
    public static void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new InvalidQueryParamException(START_DATE_AND_END_DATE_MANDATORY.getMessage());
        }
        if (startDate.isAfter(endDate)) {
            throw new InvalidQueryParamException(START_DATE_AFTER_END_DATE.getMessage());
        }
    }

    public static void validatePageRequest(int page, int pageSize) {
        if (page < 0) {
            throw new InvalidQueryParamException(PAGE_INDEX_NEGATIVE.getMessage());
        }

        if (pageSize <= 0) {
            throw new InvalidQueryParamException(PAGE_SIZE_NEGATIVE.getMessage());
        }
    }
}
