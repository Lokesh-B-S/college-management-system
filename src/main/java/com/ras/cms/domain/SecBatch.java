package com.ras.cms.domain;

import com.ras.cms.service.program.ProgramService;

import javax.persistence.*;

@Entity
public class SecBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long secBatchId;

    private String secBatchName;

    @ManyToOne
    private AcademicYear academicYear;

    @ManyToOne
    private Program program;

    @ManyToOne
    private Semester semester;

    @ManyToOne
    private Term term;

    @ManyToOne
    private Section section;

    public SecBatch(){}

    public Long getSecBatchId() {
        return secBatchId;
    }

    public void setSecBatchId(Long secBatchId) {
        this.secBatchId = secBatchId;
    }

    public String getSecBatchName() {
        return secBatchName;
    }

    public void setSecBatchName(String secBatchName) {
        this.secBatchName = secBatchName;
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

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}

