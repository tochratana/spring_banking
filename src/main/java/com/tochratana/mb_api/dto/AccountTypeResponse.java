package com.tochratana.mb_api.dto;

public record AccountTypeResponse(
        Integer id,
        String typeName,
        Boolean isDeleted
) {}