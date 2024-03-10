package com.ras.cms.domain;

import javax.persistence.*;

@Entity
public class secBatchCount {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long secBatchCountId;

    private Integer batchCount;

    @ManyToOne
    private AcademicYear academicYear;

    @ManyToOne
    private Semester semester;

    @ManyToOne
    private Term term;

    @ManyToOne
    private Program program;

    @ManyToOne
    private Section section;

    public Long getSecBatchCountId() {
        return secBatchCountId;
    }

    public void setSecBatchCountId(Long secBatchCountId) {
        this.secBatchCountId = secBatchCountId;
    }

    public Integer getBatchCount() {
        return batchCount;
    }

    public void setBatchCount(Integer batchCount) {
        this.batchCount = batchCount;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
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

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}

