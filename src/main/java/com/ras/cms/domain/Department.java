package com.ras.cms.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;
    private String departmentCode;
    private String departmentName;

    @ManyToOne
    private College college;
    //private Long CollegeId;

    //private Long courseId;

//    @ManyToOne
//    @NotNull
//    private College CollegeName;


//    public College getCollegeName() {
//        return CollegeName;
//    }
//
//    public void setCollegeName(College CollegeName) {
//        this.CollegeName = CollegeName;
//    }

//    public String getCourseName() {
//        return programName;
//    }
//
//    public void setCourseName(String programName) {
//        this.programName = programName;
//    }


    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

//    public Long getCollegeId() {
//        return CollegeId;
//    }
//
//    public void setCollegeId(Long CollegeId) {
//        this.CollegeId = CollegeId;
//    }

//    public Long getCourseId() {
//        return courseId;
//    }
//
//    public void setCourseId(Long courseId) {
//        this.courseId = courseId;
//    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

//    public Long getHodId() {
//        return hodId;
//    }
//
//    public void setHodId(Long hodId) {
//        this.hodId = hodId;
//    }
//
//    public Long getAttendentId() {
//        return attendentId;
//    }
//
//    public void setAttendentId(Long attendentId) {
//        this.attendentId = attendentId;
//    }

//    public Program getProgramName() {
//        return programName;
//    }
//
//    public void setProgramName(Program programName) {
//        this.programName = programName;
//    }
}
