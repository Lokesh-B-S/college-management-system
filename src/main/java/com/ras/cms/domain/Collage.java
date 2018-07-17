package com.ras.cms.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Collage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long collageId;

    String collageName;
    String collageAddress;
    String collageCode;

    @ManyToMany
    List<Course> courses;

    public Long getCollageId() {
        return collageId;
    }

    public void setCollageId(Long collageId) {
        this.collageId = collageId;
    }

    public String getCollageName() {
        return collageName;
    }

    public void setCollageName(String collageName) {
        this.collageName = collageName;
    }

    public String getCollageAddress() {
        return collageAddress;
    }

    public void setCollageAddress(String collageAddress) {
        this.collageAddress = collageAddress;
    }

    public String getCollageCode() {
        return collageCode;
    }

    public void setCollageCode(String collageCode) {
        this.collageCode = collageCode;
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
