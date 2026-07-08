package com.emp.ems.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepartmentRequest(

        @NotBlank(message = "Department name is required")
        @Size(min = 2, max = 100,
                message = "Department name must be between 2 and 100 characters")
        String name,

        @Size(max = 500,
                message = "Description cannot exceed 500 characters")
        String description
) {
}