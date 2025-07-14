package com.tochratana.mb_api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotBlank(message = "Account type is required")
        @Size(max = 50, message = "Account type must not exceed 50 characters")
        String actType,

        @NotBlank(message = "Account currency is required")
        @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be 3 uppercase letters (e.g., USD, EUR)")
        String actCurrency,

        @NotNull(message = "Balance is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be non-negative")
        @DecimalMax(value = "999999999999999.99", message = "Balance exceeds maximum limit")
        @Digits(integer = 15, fraction = 2, message = "Balance format is invalid")
        BigDecimal balance,

        @NotNull(message = "Customer ID is required")
        @Positive(message = "Customer ID must be positive")
        Integer customerId,

        @NotNull(message = "Account type ID is required")
        @Positive(message = "Account type ID must be positive")
        Integer accountTypeId
) {}