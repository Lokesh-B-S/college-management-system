package com.ras.cms.domain;

import javax.persistence.*;

@Entity
public class BatchYearDeptProgramSem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchYearDeptProgramSemId;
    @ManyToOne
    private Batch batch;
    @ManyToOne
    private AcademicYear academicYear;
    @ManyToOne
    private Department department;
    @ManyToOne
    private Program program;
    @ManyToOne
    private Semester semester;

    public BatchYearDeptProgramSem() {
    }

    public BatchYearDeptProgramSem(Long batchYearDeptProgramSemId, Batch batch, AcademicYear academicYear, Department department, Program program, Semester semester) {
        this.batchYearDeptProgramSemId = batchYearDeptProgramSemId;
        this.batch = batch;
        this.academicYear = academicYear;
        this.department = department;
        this.program = program;
        this.semester = semester;
    }

    public Long getBatchYearDeptProgramSemId() {
        return batchYearDeptProgramSemId;
    }

    public void setBatchYearDeptProgramSemId(Long batchYearDeptProgramSemId) {
        this.batchYearDeptProgramSemId = batchYearDeptProgramSemId;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
}
