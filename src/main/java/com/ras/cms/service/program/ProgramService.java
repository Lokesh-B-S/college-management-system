package com.ras.cms.service.program;

import com.ras.cms.domain.Department;
import com.ras.cms.domain.Program;

import java.util.List;

public interface ProgramService {

    List<Program> findAll();

    Program findOne(Long id);
    Program saveProgram(Program program);

    void deleteProgram(Long id);

    List<Program> getProgramsByDepartment(Department department);

    Program findByProgramName(String programName);
}
