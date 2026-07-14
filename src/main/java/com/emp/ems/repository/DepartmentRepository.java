package com.emp.ems.repository;

import com.emp.ems.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

// Use JpaSpecificationExecutor - This gives us dynamic searching.

public interface DepartmentRepository
        extends JpaRepository<Department, Long>,
        JpaSpecificationExecutor<Department> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}