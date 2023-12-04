package com.ras.cms.service.batchyeardeptprogramsem;

import com.ras.cms.repository.BatchYearDeptProgramSemRepository;
import com.ras.cms.domain.BatchYearDeptProgramSem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchYearDeptProgramSemServiceImpl implements BatchYearDeptProgramSemService{

    @Autowired
    BatchYearDeptProgramSemRepository batchYearDeptProgramSemRepository;


    @Override
    public BatchYearDeptProgramSem findOne(Long id) {
        return batchYearDeptProgramSemRepository.findById(id).get();
    }

    @Override
    public List<BatchYearDeptProgramSem> getAllEntries() {
        return batchYearDeptProgramSemRepository.findAll();
    }
    @Override
    public BatchYearDeptProgramSem saveEntry(BatchYearDeptProgramSem bydps){
        return batchYearDeptProgramSemRepository.save(bydps);
    }

    @Override
    public boolean existsEntry(BatchYearDeptProgramSem batchYearDeptProgramSem){
        return batchYearDeptProgramSemRepository.existsByBatchAndAcademicYearAndDepartmentAndProgramAndSemester(batchYearDeptProgramSem.getBatch(),batchYearDeptProgramSem.getAcademicYear(),batchYearDeptProgramSem.getDepartment(),batchYearDeptProgramSem.getProgram(),batchYearDeptProgramSem.getSemester());
    }

    @Override
    public Long findId(BatchYearDeptProgramSem batchYearDeptProgramSem){
        return batchYearDeptProgramSemRepository.findIdByBatchAndAcademicYearAndDepartmentAndProgramAndSemester(batchYearDeptProgramSem.getBatch(),batchYearDeptProgramSem.getAcademicYear(),batchYearDeptProgramSem.getDepartment(),batchYearDeptProgramSem.getProgram(),batchYearDeptProgramSem.getSemester());
    }

    @Override
    public BatchYearDeptProgramSem findRow(BatchYearDeptProgramSem batchYearDeptProgramSem){
        return batchYearDeptProgramSemRepository.findByBatchAndAcademicYearAndDepartmentAndProgramAndSemester(batchYearDeptProgramSem.getBatch(),batchYearDeptProgramSem.getAcademicYear(),batchYearDeptProgramSem.getDepartment(),batchYearDeptProgramSem.getProgram(),batchYearDeptProgramSem.getSemester());
    }

//    @Override
//    public Department findDepartment(Long batchYearDeptProgramSemId){return batchYearDeptProgramSemRepository.findDepartmentByBatchYearDeptProgramSemId(batchYearDeptProgramSemId);}

}
