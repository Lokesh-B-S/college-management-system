package com.ras.cms.service.coursetype;

import com.ras.cms.domain.CourseType;

import java.util.List;

public interface CourseTypeService {

    List<CourseType> findAll();

    CourseType findOne(Long id);

    CourseType getCourseTypeByTypeOfCourse(String typeOfCourse);

    CourseType saveCourseType(CourseType courseType);

    void deleteCourseType(Long id);
}
