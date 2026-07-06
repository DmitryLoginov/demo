package dev.ldv.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final ApiError apiError;
    private final String errorDescription;

    public ApiException(ApiError apiError, String errorDescription) {
        this.apiError = apiError;
        this.errorDescription = errorDescription;
    }
}