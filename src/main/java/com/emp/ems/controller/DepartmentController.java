package com.emp.ems.controller;

import com.emp.ems.common.dto.PageResponse;
import com.emp.ems.dto.DepartmentRequest;
import com.emp.ems.dto.DepartmentResponse;
import com.emp.ems.dto.DepartmentSearchRequest;
import com.emp.ems.exception.ApiErrorResponse;
import com.emp.ems.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
@Tag(
        name = "Department API",
        description = "REST APIs for managing departments"
)
public class DepartmentController {

    private final DepartmentService departmentService;

    @Operation(
            summary = "Create a department",
            description = "Creates a new department, if a department with the same name does not already exist"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Department created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Department already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse createDepartment(@Valid @RequestBody DepartmentRequest departmentRequest) {
        return departmentService.createDepartment(departmentRequest);
    }

    @Operation(
            summary = "Get all departments",
            description = "Retrieves all departments with pagination and sorting support"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departments retrieved successfully")
    })
    @GetMapping
    public PageResponse<DepartmentResponse> getAllDepartments(@ParameterObject Pageable pageable) {
        return departmentService.getAllDepartments(pageable);
    }

    @Operation(
            summary = "Get department by ID",
            description = "Retrieves a department using its unique identifier"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Department found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Department not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @GetMapping("/{id}")
    public DepartmentResponse getDepartment(
            @Parameter(description = "Department ID", example = "1")
            @PathVariable Long id) {
        return departmentService.getDepartment(id);
    }

    @Operation(
            summary = "Update department",
            description = "Updates an existing department"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Department updated successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Department not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Department already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PutMapping("/{id}")
    public DepartmentResponse updateDepartment(
            @Parameter(description = "Department ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest departmentRequest) {
        return departmentService.updateDepartment(id, departmentRequest);
    }

    @Operation(
            summary = "Delete department",
            description = "Deletes a department by its unique identifier"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Department deleted successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Department not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(
            @Parameter(description = "Department ID", example = "1")
            @PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

    @Operation(
            summary = "Search departments",
            description = "Searches departments dynamically using the supplied search criteria with pagination and sorting support"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid search request",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PostMapping("/search")
    public PageResponse<DepartmentResponse> searchDepartments(@Valid @RequestBody DepartmentSearchRequest departmentSearchRequest,
                                                              @ParameterObject Pageable pageable) {
        return departmentService.searchDepartments(departmentSearchRequest, pageable);
    }
}