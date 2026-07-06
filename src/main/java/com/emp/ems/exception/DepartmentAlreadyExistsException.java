package com.emp.ems.exception;
public class DepartmentAlreadyExistsException extends RuntimeException {
    public DepartmentAlreadyExistsException(String name) {
        super("Department already exists with name: " + name);
    }
}