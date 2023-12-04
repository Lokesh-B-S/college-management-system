package com.ras.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TeachingDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long teachingDepartmentId;

    private String teachingDepartmentName;

    private String teachingDepartmentCode;


    public TeachingDepartment() {
    }

    public TeachingDepartment(Long teachingDepartmentId, String teachingDepartmentName, String teachingDepartmentCode) {
        this.teachingDepartmentId = teachingDepartmentId;
        this.teachingDepartmentName = teachingDepartmentName;
        this.teachingDepartmentCode = teachingDepartmentCode;
    }

    public Long getTeachingDepartmentId() {
        return teachingDepartmentId;
    }

    public void setTeachingDepartmentId(Long teachingDepartmentId) {
        this.teachingDepartmentId = teachingDepartmentId;
    }

    public String getTeachingDepartmentName() {
        return teachingDepartmentName;
    }

    public void setTeachingDepartmentName(String teachingDepartmentName) {
        this.teachingDepartmentName = teachingDepartmentName;
    }

    public String getTeachingDepartmentCode() {
        return teachingDepartmentCode;
    }

    public void setTeachingDepartmentCode(String teachingDepartmentCode) {
        this.teachingDepartmentCode = teachingDepartmentCode;
    }
}
