package com.ras.cms.service.coursefaculty;

import com.ras.cms.repository.CourseFacultyRepository;
import com.ras.cms.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseFacultyServiceImpl implements CourseFacultyService{

    @Autowired
    CourseFacultyRepository courseFacultyRepository;

    @Override
    public List<CourseFaculty> findAll(){return courseFacultyRepository.findAll();}

    @Override
    public CourseFaculty findOne(Long id){return courseFacultyRepository.findById(id).get();}
    @Override
    public CourseFaculty saveCourseFaculty(CourseFaculty courseFaculty){return courseFacultyRepository.save(courseFaculty);}
    @Override
    public void deleteCourseFaculty(Long id){courseFacultyRepository.deleteById(id);}

    @Override
    public List<CourseFaculty> getAllotmentsByDepartmentAndSemesterAndSection(Department department, Semester semester, Section section){return courseFacultyRepository.findAllByDepartmentAndSemesterAndSection(department,semester,section);}
}
