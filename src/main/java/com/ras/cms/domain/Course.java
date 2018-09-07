package com.ras.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long courseId;
    private String courseCode;
    private String courseName;
    private Long courseDuration; // in Years
    private Long semister;//No of Semisters

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(Long courseDuration) {
        this.courseDuration = courseDuration;
    }

    public Long getSemister() {
        return semister;
    }

    public void setSemister(Long semister) {
        this.semister = semister;
    }
}
