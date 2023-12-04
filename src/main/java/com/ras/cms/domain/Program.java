package com.ras.cms.domain;

import javax.persistence.*;

@Entity
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programId;

    @ManyToOne
    private Department department;
    private String programName;

    private String programCode;

    public Program(){}

//    public Program(Long programId, String programName, Long programCode) {
//        this.programId = programId;
//        this.programName = programName;
//        this.programCode = programCode;
//    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
