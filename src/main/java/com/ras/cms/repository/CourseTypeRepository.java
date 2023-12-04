package com.ras.cms.repository;

import com.ras.cms.domain.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseTypeRepository extends JpaRepository<CourseType, Long> {

    CourseType findByTypeOfCourse(String typeOfCourse);
}
