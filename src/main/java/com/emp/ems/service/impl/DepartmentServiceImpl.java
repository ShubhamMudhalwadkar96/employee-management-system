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
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) {
        if (departmentRepository.existsByName(departmentRequest.name())) {
            throw new DepartmentAlreadyExistsException(
                    "Department already exists with name: " + departmentRequest.name());
        }
        Department department = departmentMapper.toEntity(departmentRequest);

        Department savedDepartment = departmentRepository.save(department);

        return departmentMapper.toResponse(savedDepartment);
    }

    @Override
    public DepartmentResponse getDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new DepartmentNotFoundException(id));

        return departmentMapper.toResponse(department);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DepartmentResponse> getAllDepartments(Pageable pageable) {

        Page<DepartmentResponse> page = departmentRepository
                .findAll(pageable)
                .map(departmentMapper::toResponse);

        return PageUtils.toPageResponse(page);
    }

    @Override
    public DepartmentResponse updateDepartment(Long id,
                                               DepartmentRequest departmentRequest) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Department not found"));

        departmentMapper.updateEntity(departmentRequest, department);

        Department updatedDepartment =
                departmentRepository.save(department);

        return departmentMapper.toResponse(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Department not found");
        }

        departmentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DepartmentResponse> searchDepartments(DepartmentSearchRequest departmentSearchRequest,
                                                              Pageable pageable) {
        Page<DepartmentResponse> page = departmentRepository
                .findAll(DepartmentSpecification.search(departmentSearchRequest), pageable)
                .map(departmentMapper::toResponse);
        return PageUtils.toPageResponse(page);
    }
}
