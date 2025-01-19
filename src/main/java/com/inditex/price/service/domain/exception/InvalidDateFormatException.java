package com.inditex.price.service.domain.exception;

public class InvalidDateFormatException  extends RuntimeException {
    public InvalidDateFormatException(String message) {
        super(message);
    }
}