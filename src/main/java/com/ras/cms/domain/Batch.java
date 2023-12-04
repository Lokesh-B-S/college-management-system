package com.ras.cms.domain;

import javax.persistence.*;

@Entity
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;

    private String batchName;
    @ManyToOne
    private Department department;
    @ManyToOne
    private Program program;

    public Batch() {
    }

    public Batch(Long batchId, String batchName, Department department, Program program) {
        this.batchId = batchId;
        this.batchName = batchName;
        this.department = department;
        this.program = program;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
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
}
