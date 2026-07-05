package com.emp.ems.mapper;

import com.emp.ems.dto.EmployeeRequest;
import com.emp.ems.dto.EmployeeResponse;
import com.emp.ems.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    Employee toEntity(EmployeeRequest request);

    @Mapping(target = "fullName",
            expression = "java(employee.getFirstName() + \" \" + employee.getLastName())")
    @Mapping(target = "departmentName",
            source = "department.name")
    EmployeeResponse toResponse(Employee employee);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntity(EmployeeRequest employeeRequest,
                      @MappingTarget Employee employee);
}