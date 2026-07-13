package com.emp.ems.service;

import com.emp.ems.common.dto.PageResponse;
import com.emp.ems.dto.DepartmentRequest;
import com.emp.ems.dto.DepartmentResponse;
import com.emp.ems.dto.DepartmentSearchRequest;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing department operations.
 *
 * <p>
 * Defines business operations for creating, retrieving, updating,
 * deleting, listing, and searching departments.
 * </p>
 */
public interface DepartmentService {

    /**
     * Creates a new department.
     *
     * <p>
     * Validates that a department with the given name does not already exist
     * before persisting it.
     * </p>
     *
     * @param request request containing department details
     * @return the created department
     */
    DepartmentResponse createDepartment(DepartmentRequest request);

    /**
     * Retrieves a department by its unique identifier.
     *
     * @param id department identifier
     * @return department details
     */
    DepartmentResponse getDepartment(Long id);

    /**
     * Retrieves all departments with pagination and sorting support.
     *
     * @param pageable pagination and sorting information
     * @return paginated list of departments
     */
    PageResponse<DepartmentResponse> getAllDepartments(Pageable pageable);

    /**
     * Updates an existing department.
     *
     * @param id department identifier
     * @param request updated department details
     * @return updated department
     */
    DepartmentResponse updateDepartment(Long id, DepartmentRequest request);

    /**
     * Deletes a department by its unique identifier.
     *
     * @param id department identifier
     */
    void deleteDepartment(Long id);

    /**
     * Searches departments based on the supplied search criteria.
     *
     * <p>
     * Supports dynamic filtering along with pagination and sorting.
     * </p>
     *
     * @param departmentSearchRequest search criteria
     * @param pageable pagination and sorting information
     * @return paginated list of matching departments
     */
    PageResponse<DepartmentResponse> searchDepartments(DepartmentSearchRequest departmentSearchRequest,
                                                       Pageable pageable);
}