package com.ras.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;
    private String branchCode;
    private String branchName;
    private Long hodId;
    private Long attendentId;
    private Long CollegeId;
    private Long courseId;
    private String CollegeName;
    private String courseName;

    public String getCollegeName() {
        return CollegeName;
    }

    public void setCollegeName(String CollegeName) {
        this.CollegeName = CollegeName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public Long getCollegeId() {
        return CollegeId;
    }

    public void setCollegeId(Long CollegeId) {
        this.CollegeId = CollegeId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Long getHodId() {
        return hodId;
    }

    public void setHodId(Long hodId) {
        this.hodId = hodId;
    }

    public Long getAttendentId() {
        return attendentId;
    }

    public void setAttendentId(Long attendentId) {
        this.attendentId = attendentId;
    }
}
