package com.ras.cms.service.course;

import com.ras.cms.domain.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    Course findOne(Long id);
    Course saveCourse(Course collage);
    void deleteCourse(Long id);
}
