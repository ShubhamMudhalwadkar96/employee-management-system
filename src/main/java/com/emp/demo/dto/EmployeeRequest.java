package com.emp.demo.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeRequest(@NotBlank String employeeCode, @NotBlank String firstName, @NotBlank String lastName,
                              @Email String email, String phoneNumber, @NotNull BigDecimal salary,
                              @NotNull LocalDate hireDate, @NotNull Long departmentId) {}