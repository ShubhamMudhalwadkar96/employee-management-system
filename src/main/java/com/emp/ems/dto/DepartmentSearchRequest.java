package com.emp.ems.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Search criteria for filtering departments")
public record DepartmentSearchRequest(
        @Schema(
                description = "Search by department name(case-insensitive, partial match)",
                example = "IT"
        )
        String name,

        @Schema(
                description = "Search by department description(case-insensitive, partial match)",
                example = "Technology"
        )
        String description
) {
}