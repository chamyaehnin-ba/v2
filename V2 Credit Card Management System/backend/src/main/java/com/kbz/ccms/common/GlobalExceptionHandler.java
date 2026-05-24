package com.kbz.ccms.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(BusinessException.class)
  ResponseEntity<ApiResponse<Void>> handleBusiness(BusinessException ex) {
    return ResponseEntity.badRequest().body(ApiResponse.error(ex.code(), ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
    FieldError first = ex.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);
    String message = first == null ? "Validation failed" : first.getField() + ": " + first.getDefaultMessage();
    return ResponseEntity.badRequest().body(ApiResponse.error("MSG016", message));
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<ApiResponse<Void>> handleUnexpected(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.error("1002", "Unknown Error"));
  }
}

