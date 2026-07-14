package com.emp.ems.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object containing department details")
public record DepartmentResponse(
        @Schema(
                description = "Unique identifier of the department",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Name of the department",
                example = "Information Technology"
        )
        String name,

        @Schema(
                description = "Brief description of the department",
                example = "Responsible for software development and infrastructure"
        )
        String description) {
}