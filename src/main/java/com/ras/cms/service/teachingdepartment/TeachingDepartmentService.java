package com.ras.cms.service.teachingdepartment;

import com.ras.cms.domain.TeachingDepartment;
import com.ras.cms.domain.Department;
import com.ras.cms.domain.TeachingDepartment;

import java.util.List;

public interface TeachingDepartmentService {

    List<TeachingDepartment> findAll();

    TeachingDepartment findOne(Long id);
    TeachingDepartment saveTeachingDepartment(TeachingDepartment TeachingDepartment);

    void deleteTeachingDepartment(Long id);

    //List<TeachingDepartment> getTeachingDepartmentesByDepartment(Department department);
}
