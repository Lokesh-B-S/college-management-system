package com.ras.cms.service.department;

import com.ras.cms.dao.DepartmentRepository;
import com.ras.cms.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findOne(Long id) {
        return departmentRepository.findById(id).get();
    }


    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    //public Department findByDepartmentName(Department department){return departmentRepository.}
}