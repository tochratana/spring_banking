package com.tochratana.mb_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ServiceException {


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleServiceException(ResponseStatusException e){
        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .message("Business Logic error")
                .status(e.getStatusCode().value())
                .localDateTime(LocalDateTime.now())
                .detail(e.getReason())
                .build();

        return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse<Map<String, String>> errorResponse = ErrorResponse.<Map<String, String>>builder()
                .message("Validation Failed")
                .status(HttpStatus.BAD_REQUEST.value())
                .localDateTime(LocalDateTime.now())
                .detail(errors)
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<String>> handleGenericException(Exception ex) {
        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .message("Internal Server Error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .localDateTime(LocalDateTime.now())
                .detail(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}