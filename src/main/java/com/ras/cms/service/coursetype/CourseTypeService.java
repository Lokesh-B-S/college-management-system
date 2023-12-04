package com.ras.cms.service.coursetype;

import com.ras.cms.domain.CourseType;

import java.util.List;

public interface CourseTypeService {

    List<CourseType> findAll();

    CourseType getCourseTypeByTypeOfCourse(String typeOfCourse);

    CourseType saveCourseType(CourseType courseType);
}
