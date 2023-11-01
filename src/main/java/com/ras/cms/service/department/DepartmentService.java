package com.ras.cms.service.department;

import com.ras.cms.domain.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();
    Department findOne(Long id);
    Department saveDepartment(Department department);
    void deleteDepartment(Long id);
}