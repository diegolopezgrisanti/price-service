package com.inditex.price.service.infrastructure.entrypoint.rest;

import com.inditex.price.service.domain.exception.InvalidDateFormatException;
import com.inditex.price.service.domain.exception.PriceNotFoundException;
import com.inditex.price.service.infrastructure.entrypoint.rest.response.error.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlePriceNotFound(PriceNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage());
        return ResponseEntity.status(404).body(errorResponse); // 404 Not Found
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidDateFormat(InvalidDateFormatException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage());
        return ResponseEntity.status(400).body(errorResponse); // 400 Bad Request
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("An unexpected error occurred: " + ex.getMessage());
        return ResponseEntity.status(500).body(errorResponse); // 500 Internal Server Error
    }
}