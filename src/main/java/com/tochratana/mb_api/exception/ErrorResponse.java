package com.tochratana.mb_api.exception;
import lombok.Builder;
import java.time.LocalDateTime;
@Builder
public record ErrorResponse <T>(
        String  message,
        Integer status,
        LocalDateTime localDateTime,
        T detail

) {
}
