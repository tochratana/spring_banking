package com.tochratana.mb_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCustomerRequest(

        @NotBlank(message = "Full Name is require")
        String fullName,

        String email,

        String gender,
        String phoneNumber,
        String remark
) {
}
