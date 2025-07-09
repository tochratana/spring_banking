package com.tochratana.mb_api.dto;

public record CustomerResponse(
        String fullName,
        String gender,
        String email
) {
}
