package com.emp.ems.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Global exception handler for translating application exceptions
 * into standardized API error responses.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleDepartmentNotFound(DepartmentNotFoundException ex,
                                                                     HttpServletRequest request) {
        log.warn("Department not found: {}", ex.getMessage());

        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
    }

    @ExceptionHandler(DepartmentAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleDepartmentAlreadyExists(DepartmentAlreadyExistsException ex,
                                                                          HttpServletRequest request) {
        log.warn("Department already exists: {}", ex.getMessage());

        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request, null);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException ex,
                                                                   HttpServletRequest request) {
        log.warn("Employee not found: {}", ex.getMessage());

        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
    }

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleEmployeeAlreadyExists(EmployeeAlreadyExistsException ex,
                                                                        HttpServletRequest request) {
        log.warn("Employee already exists: {}", ex.getMessage());

        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex,
                                                                      HttpServletRequest request) {
        log.warn("Validation failed for request: {}", request.getRequestURI());

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> Objects.requireNonNullElse(
                                error.getDefaultMessage(),
                                "Validation failed"
                        ),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));

        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Request validation failed", request, errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected exception occurred while processing request: {}",
                request.getRequestURI(), ex);

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.",
                request, null);
    }

    /**
     * Builds a standardized API error response.
     *
     * @param status  HTTP status
     * @param message Error message
     * @param request Current HTTP request
     * @param errors  Validation errors (if any)
     * @return ResponseEntity containing ApiErrorResponse
     */
    private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status, String message,
                                                                HttpServletRequest request,
                                                                Map<String, String> errors) {
        ApiErrorResponse response = new ApiErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                errors
        );

        return ResponseEntity.status(status).body(response);
    }
}