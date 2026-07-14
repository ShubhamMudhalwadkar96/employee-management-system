package com.emp.ems.service.impl;

import com.emp.ems.common.dto.PageResponse;
import com.emp.ems.common.util.PageUtils;
import com.emp.ems.dto.DepartmentRequest;
import com.emp.ems.dto.DepartmentResponse;
import com.emp.ems.dto.DepartmentSearchRequest;
import com.emp.ems.entity.Department;
import com.emp.ems.exception.DepartmentAlreadyExistsException;
import com.emp.ems.exception.DepartmentNotFoundException;
import com.emp.ems.mapper.DepartmentMapper;
import com.emp.ems.repository.DepartmentRepository;
import com.emp.ems.service.DepartmentService;
import com.emp.ems.specification.DepartmentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    @Transactional
    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) {
        log.info("Creating department with name: {}", departmentRequest.name());
        if (departmentRepository.existsByName(departmentRequest.name())) {
            log.warn("Department already exists with name: {}", departmentRequest.name());
            throw new DepartmentAlreadyExistsException(departmentRequest.name());
        }
        Department department = departmentMapper.toEntity(departmentRequest);

        Department savedDepartment = departmentRepository.save(department);
        log.info("Department created successfully with id: {}", savedDepartment.getId());

        return departmentMapper.toResponse(savedDepartment);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getDepartment(Long id) {
        log.info("Fetching department with id: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Department not found with id: {}", id);
                    return new DepartmentNotFoundException(id);
                });

        log.info("Department fetched successfully with id: {}", id);
        return departmentMapper.toResponse(department);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DepartmentResponse> getAllDepartments(Pageable pageable) {
        log.info("Fetching departments. Page: {}, Size: {}, Sort: {}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort());

        Page<DepartmentResponse> page = departmentRepository
                .findAll(pageable)
                .map(departmentMapper::toResponse);

        log.info("Successfully fetched {} departments. Total elements: {}, Total pages: {}",
                page.getNumberOfElements(),
                page.getTotalElements(),
                page.getTotalPages());

        return PageUtils.toPageResponse(page);
    }

    @Override
    @Transactional
    public DepartmentResponse updateDepartment(Long id,
                                               DepartmentRequest departmentRequest) {
        log.info("Updating department with id: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Department not found with id: {}", id);
                    return new DepartmentNotFoundException(id);
                });

        if (departmentRepository.existsByNameAndIdNot(departmentRequest.name(), id)) {
            log.warn("Department already exists with name: {}", departmentRequest.name());
            throw new DepartmentAlreadyExistsException(departmentRequest.name());
        }

        departmentMapper.updateEntity(departmentRequest, department);

        Department updatedDepartment = departmentRepository.save(department);

        log.info("Department updated successfully. Id: {}, Updated Name: {}",
                updatedDepartment.getId(),
                updatedDepartment.getName());

        return departmentMapper.toResponse(updatedDepartment);
    }

    @Override
    @Transactional
    public void deleteDepartment(Long id) {
        log.info("Deleting department with id: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Department not found with id: {}", id);
                    return new DepartmentNotFoundException(id);
                });

        departmentRepository.delete(department);
        log.info("Department deleted successfully with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DepartmentResponse> searchDepartments(DepartmentSearchRequest departmentSearchRequest,
                                                              Pageable pageable) {
        log.info("Searching departments. Criteria: {}, Page: {}, Size: {}, Sort: {}",
                departmentSearchRequest,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort());

        Page<DepartmentResponse> page = departmentRepository
                .findAll(DepartmentSpecification.search(departmentSearchRequest), pageable)
                .map(departmentMapper::toResponse);

        log.info("Department search completed. Records found: {}, Total elements: {}, Total pages: {}",
                page.getNumberOfElements(),
                page.getTotalElements(),
                page.getTotalPages());

        return PageUtils.toPageResponse(page);
    }
}
