package com.ras.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CourseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long courseTypeId;

    String typeOfCourse;

    public CourseType() {
    }

    public CourseType(String typeOfCourse) {
        this.typeOfCourse = typeOfCourse;
    }

    public Long getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(Long courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getTypeOfCourse() {
        return typeOfCourse;
    }

    public void setTypeOfCourse(String typeOfCourse) {
        this.typeOfCourse = typeOfCourse;
    }
}
