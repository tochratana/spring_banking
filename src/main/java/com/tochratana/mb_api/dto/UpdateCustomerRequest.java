package com.tochratana.mb_api.dto;

public record UpdateCustomerRequest(
        String fullName,
        String gender,
        String remark
) {
}
