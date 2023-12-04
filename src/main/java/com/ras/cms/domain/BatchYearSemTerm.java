package com.ras.cms.domain;

import javax.persistence.*;

@Entity
public class BatchYearSemTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchYearSemTermId;
    @ManyToOne
    private Batch batch;
    @ManyToOne
    private AcademicYear academicYear;
    @ManyToOne
    private Semester semester;
    @ManyToOne
    private Term term;


    public BatchYearSemTerm() {
    }

    public BatchYearSemTerm(Long batchYearSemTermId, Batch batch, AcademicYear academicYear, Semester semester, Term term) {
        this.batchYearSemTermId = batchYearSemTermId;
        this.batch = batch;
        this.academicYear = academicYear;
        this.semester = semester;
        this.term = term;
    }

    public Long getBatchYearSemTermId() {
        return batchYearSemTermId;
    }

    public void setBatchYearSemTermId(Long batchYearSemTermId) {
        this.batchYearSemTermId = batchYearSemTermId;
    }



    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
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
}
