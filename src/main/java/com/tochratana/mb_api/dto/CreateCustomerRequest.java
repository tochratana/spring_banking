package com.tochratana.mb_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCustomerRequest(
        @NotBlank(message = "Full Name is required")
        String fullName,

        String email,

        String gender,
        String phoneNumber,
        String remark,

        @NotBlank(message = "National Card ID is required")
        String nationalCardId,

        @NotNull(message = "Segment ID is required")
        @Positive(message = "Segment ID must be positive")
        Integer segmentId
) {}