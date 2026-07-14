package com.emp.ems.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request object for creating or updating a department")
public record DepartmentRequest(
        @Schema(
                description = "Unique name of the department",
                example = "Information Technology",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Department name is required")
        @Size(
                min = 2,
                max = 100,
                message = "Department name must be between 2 and 100 characters"
        )
        String name,

        @Schema(
                description = "Brief description of the department",
                example = "Responsible for software development and infrastructure"
        )
        @Size(
                max = 500,
                message = "Description cannot exceed 500 characters"
        )
        String description
) {
}