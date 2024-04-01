package com.ras.cms.domain;

import javax.persistence.*;

@Entity
public class GroupConfiguration {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AcademicYear academicYear;

    @ManyToOne
    private Program program;

    @ManyToOne
    private Semester semester;

    @ManyToOne
    private Term term;

    @ManyToOne
    private OpenElective openElective;



    private Integer noOfGroups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public OpenElective getOpenElective() {
        return openElective;
    }

    public void setOpenElective(OpenElective openElective) {
        this.openElective = openElective;
    }

    public Integer getNoOfGroups() {
        return noOfGroups;
    }

    public void setNoOfGroups(Integer noOfGroups) {
        this.noOfGroups = noOfGroups;
    }
}
