package com.emp.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record DepartmentRequest(@NotBlank String name, String description) {
}