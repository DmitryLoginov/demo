package dev.ldv.exception;

import lombok.Getter;

@Getter
public enum ApiError {
    BAD_REQUEST(400),
    NOT_FOUND(404),
    CONFLICT(409),
    INTERNAL_SERVER_ERROR(500);

    private final int statusCode;
    
    ApiError(int statusCode) {
        this.statusCode = statusCode;
    }
}