package dev.ldv.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final ApiError apiError;

    public ApiException(ApiError apiError, String message) {
        super(message);
        this.apiError = apiError;
    }
}