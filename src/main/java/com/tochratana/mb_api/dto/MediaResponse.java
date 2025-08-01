package com.tochratana.mb_api.dto;

import lombok.Builder;

@Builder
public record MediaResponse(
        String name,
        String mimeType,
        String uri,
        Long size
) {
}
