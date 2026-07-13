package com.emp.ems.specification;

import com.emp.ems.dto.DepartmentSearchRequest;
import com.emp.ems.entity.Department;
import org.springframework.data.jpa.domain.Specification;

public final class DepartmentSpecification {

    public DepartmentSpecification() {
    }

    /**
     * Builds a dynamic {@link Specification} for searching {@link Department}
     * entities based on the provided search criteria.
     *
     * <p>
     * Only non-null and non-blank fields from the {@link DepartmentSearchRequest}
     * are considered while constructing the query. If no search criteria are
     * provided, the returned specification matches all departments.
     * </p>
     *
     * <p>
     * Currently supported search criteria:
     * <ul>
     *     <li>Department name (case-insensitive, partial match)</li>
     *     <li>Department description (case-insensitive, partial match)</li>
     * </ul>
     * </p>
     *
     * @param departmentSearchRequest the department search criteria
     * @return a {@link Specification} that can be used with
     * {@code JpaSpecificationExecutor#findAll(Specification, Pageable)}
     */
    public static Specification<Department> search(DepartmentSearchRequest departmentSearchRequest) {
        return (root, query, criteriaBuilder) -> {

            // Start with an empty predicate (equivalent to WHERE 1 = 1)
            var predicate = criteriaBuilder.conjunction();

            // Add name filter if provided
            if (departmentSearchRequest.name()!=null && !departmentSearchRequest.name().isBlank()) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                departmentSearchRequest.name().toLowerCase() + "%"
                        )
                );
            }

            // Add description filter if provided
            if (departmentSearchRequest.description() != null && !departmentSearchRequest.description().isBlank()) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("description")),
                                "%" + departmentSearchRequest.description().toLowerCase() + "%"
                        )
                );
            }
            // Return the dynamically built search criteria
            return predicate;
        };
    }
}