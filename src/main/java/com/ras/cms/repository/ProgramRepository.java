package com.ras.cms.repository;

import com.ras.cms.domain.Department;
import com.ras.cms.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    List<Program> findAllByDepartment(Department department);

    Program findByProgramName(String programName);
}
