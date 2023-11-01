package com.ras.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long subjectId;

    Long CollegeId;
    Long courseId;
    Long departmentId;
    Long subjectTypeId;
    String subjectName;
    String subjectCode;
    String subjectDescription;
    Double subjectScore;
    Double maxMarks;
    Double passingMarks;
    String academicYear;
    Long semisterId;

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId){
        this.subjectId = subjectId;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getSubjectTypeId() {
        return subjectTypeId;
    }

    public void setSubjectTypeId(Long subjectTypeId) {
        this.subjectTypeId = subjectTypeId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public Double getSubjectScore() {
        return subjectScore;
    }

    public void setSubjectScore(Double subjectScore) {
        this.subjectScore = subjectScore;
    }

    public Double getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Double maxMarks) {
        this.maxMarks = maxMarks;
    }

    public Double getPassingMarks() {
        return passingMarks;
    }

    public void setPassingMarks(Double passingMarks) {
        this.passingMarks = passingMarks;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Long getSemisterId() {
        return semisterId;
    }

    public void setSemisterId(Long semisterId) {
        this.semisterId = semisterId;
    }
}
