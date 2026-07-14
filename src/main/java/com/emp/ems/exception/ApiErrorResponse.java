package com.emp.ems.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Map;

@Schema(description = "Standard API error response")
public record ApiErrorResponse(
        @Schema(
                description = "Timestamp when the error occurred",
                example = "2026-07-14T10:15:30Z"
        )
        Instant timestamp,

        @Schema(
                description = "HTTP status code",
                example = "404"
        )
        int status,

        @Schema(
                description = "HTTP status description",
                example = "Not Found"
        )
        String error,

        @Schema(
                description = "Detailed error message",
                example = "Department not found with id: 10"
        )
        String message,

        @Schema(
                description = "API endpoint that generated the error",
                example = "/api/v1/departments/10"
        )
        String path,

        @Schema(
                description = "Validation errors mapped by field name"
        )
        Map<String, String> errors
) {
}