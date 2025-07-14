package com.tochratana.mb_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
        Integer id,
        String actType,
        String actCurrency,
        BigDecimal balance,
        Boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        CustomerSummary customer,
        AccountTypeSummary accountType
) {
    public record CustomerSummary(
            Integer id,
            String name,
            String email
    ) {}

    public record AccountTypeSummary(
            Integer id,
            String name,
            String description
    ) {}
}