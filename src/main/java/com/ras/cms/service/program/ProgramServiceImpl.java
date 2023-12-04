package com.ras.cms.service.program;

import com.ras.cms.repository.ProgramRepository;
import com.ras.cms.domain.Department;
import com.ras.cms.domain.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService{

    @Autowired
    ProgramRepository programRepository;

    @Override
    public List<Program> findAll(){return programRepository.findAll();}

    @Override
    public Program findOne(Long id){return programRepository.findById(id).get();}
    @Override
    public Program saveProgram(Program program){return programRepository.save(program);}
    @Override
    public void deleteProgram(Long id){programRepository.deleteById(id);}

    @Override
    public List<Program> getProgramsByDepartment(Department department){return programRepository.findAllByDepartment(department);}

    @Override
    public Program findByProgramName(String programName){return programRepository.findByProgramName(programName);}

}
