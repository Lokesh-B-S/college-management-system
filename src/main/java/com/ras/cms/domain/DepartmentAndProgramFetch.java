package com.ras.cms.domain;

import org.springframework.security.core.parameters.P;

import javax.persistence.*;

@Entity
public class DepartmentAndProgramFetch {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long DepartmentAndProgramFetchId;

@ManyToOne
    private Program program;
@ManyToOne
    private Department department;

    public DepartmentAndProgramFetch() {
    }

    public Long getDepartmentAndProgramFetchId() {
        return DepartmentAndProgramFetchId;
    }

    public void setDepartmentAndProgramFetchId(Long departmentAndProgramFetchId) {
        DepartmentAndProgramFetchId = departmentAndProgramFetchId;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
