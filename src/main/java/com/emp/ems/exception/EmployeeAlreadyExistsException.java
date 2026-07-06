package com.emp.ems.exception;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException(String employeeCode) {
        super("Employee already exists with employee code: " + employeeCode);
    }
}