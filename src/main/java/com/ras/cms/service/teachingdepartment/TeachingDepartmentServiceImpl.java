package com.ras.cms.service.teachingdepartment;

import com.ras.cms.repository.TeachingDepartmentRepository;
import com.ras.cms.domain.TeachingDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeachingDepartmentServiceImpl implements TeachingDepartmentService{

    @Autowired
    TeachingDepartmentRepository teachingDepartmentRepository;


    public List<TeachingDepartment> findAll() {
        return teachingDepartmentRepository.findAll();
    }

    public TeachingDepartment findOne(Long id) {
        return teachingDepartmentRepository.findById(id).get();
    }

    public TeachingDepartment saveTeachingDepartment(TeachingDepartment teachingDepartment) {
        return teachingDepartmentRepository.save(teachingDepartment);
    }

    public void deleteTeachingDepartment(Long id) {
        teachingDepartmentRepository.deleteById(id);
    }

//    public List<TeachingDepartment> getTeachingDepartmentesByDepartment(Department department) {
//        return teachingDepartmentRepository.findAllByDepartment(department);
//    }


}
