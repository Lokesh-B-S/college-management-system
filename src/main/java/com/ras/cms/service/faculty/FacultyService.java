package com.ras.cms.service.faculty;

import com.ras.cms.domain.*;

import java.util.List;

public interface FacultyService {

    List<Faculty> findAll();
    Faculty findOne(Long id);
    Faculty saveFaculty(Faculty faculty);
    void deleteFaculty(Long id);

    boolean existsFacultyByDepartment(Department department);

    List<Faculty> getFacultiesByDepartment(Department department);


}
