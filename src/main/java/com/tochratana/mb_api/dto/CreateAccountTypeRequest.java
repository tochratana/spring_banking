package com.tochratana.mb_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAccountTypeRequest(
        @NotBlank(message = "Type name is required")
        @Size(max = 50, message = "Type name must not exceed 50 characters")
        String typeName
) {}