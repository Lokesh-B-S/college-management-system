package com.ras.cms.domain;

import javax.persistence.*;

@Entity
public class DepartmentSemSec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentSemSecId;

    @ManyToOne
    private Department department;
    @ManyToOne
    private Semester semester;
    @ManyToOne
    private Section section;

    public DepartmentSemSec(){}

    public DepartmentSemSec(Department department, Semester semester, Section section) {
        this.department = department;
        this.semester = semester;
        this.section = section;
    }

    public Long getDepartmentSemSecId() {
        return departmentSemSecId;
    }

    public void setDepartmentSemSecId(Long departmentSemSecId) {
        this.departmentSemSecId = departmentSemSecId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
