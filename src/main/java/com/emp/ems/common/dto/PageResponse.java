package com.emp.ems.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Generic paginated response")
public record PageResponse<T>(
        @Schema(description = "List of records for the current page")
        List<T> content,

        @Schema(
                description = "Current page number(0-based)",
                example = "0"
        )
        int page,

        @Schema(
                description = "Number of records requested per page",
                example = "10"
        )
        int size,

        @Schema(
                description = "Total number of available records",
                example = "125"
        )
        long totalElements,

        @Schema(
                description = "Total number of available pages",
                example = "13"
        )
        int totalPages,

        @Schema(
                description = "Indicates whether this is the first page",
                example = "true"
        )
        boolean first,

        @Schema(
                description = "Indicates whether this is the last page",
                example = "false"
        )
        boolean last
) {
}