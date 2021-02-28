package com.ras.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long CollegeId;

    String CollegeName;
    String CollegeAddress;
    String CollegeCode;

    @ManyToMany
    List<Course> courses;

    public Long getCollegeId() {
        return CollegeId;
    }

    public void setCollegeId(Long CollegeId) {
        this.CollegeId = CollegeId;
    }

    public String getCollegeName() {
        return CollegeName;
    }

    public void setCollegeName(String CollegeName) {
        this.CollegeName = CollegeName;
    }

    public String getCollegeAddress() {
        return CollegeAddress;
    }

    public void setCollegeAddress(String CollegeAddress) {
        this.CollegeAddress = CollegeAddress;
    }

    public String getCollegeCode() {
        return CollegeCode;
    }

    public void setCollegeCode(String CollegeCode) {
        this.CollegeCode = CollegeCode;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public boolean hasCourse(Course course) {
        for (Course containedSkill: getCourses()) {
            if (containedSkill.getCourseId() == course.getCourseId()) {
                return true;
            }
        }
        return false;
    }
}