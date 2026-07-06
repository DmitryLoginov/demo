package dev.ldv.controller;

import dev.ldv.dto.ErrorDto;
import dev.ldv.exception.ApiError;
import dev.ldv.exception.ApiException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDto> handleApiException(ApiException ex) {
        ApiError apiError = ex.getApiError();
        String errorDescription = ex.getErrorDescription();

        log.warn("{} {}", apiError.getStatusCode(), ex.getMessage(), ex);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        ex.printStackTrace(printWriter);

        ErrorDto errorDto = new ErrorDto(apiError.name(), errorDescription, apiError.getStatusCode());

        return new ResponseEntity<>(errorDto, HttpStatus.valueOf(apiError.getStatusCode()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleJakartaConstraintViolationException(ConstraintViolationException ex) {
        log.warn("400 {}", ex.getMessage(), ex);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        ex.printStackTrace(printWriter);

        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(constraintViolation -> {
            String propertyName = constraintViolation.getPropertyPath().toString();
            String errorMessage = constraintViolation.getMessage();

            errors.put(propertyName, errorMessage);
        });

        return new ErrorDto(ApiError.BAD_REQUEST.name(),
                errors.entrySet().stream()
                        .map(entry -> entry.getKey() + ": " + entry.getValue())
                        .collect(Collectors.joining("; ")),
                ApiError.BAD_REQUEST.getStatusCode());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleException(Exception ex) {
        log.warn("500 {}", ex.getMessage(), ex);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        ex.printStackTrace(printWriter);

        return new ErrorDto(ApiError.INTERNAL_SERVER_ERROR.name(), "Unexpected error",
                ApiError.INTERNAL_SERVER_ERROR.getStatusCode());
    }
}