package com.ras.cms.service.batchyeardeptprogramsem;

import com.ras.cms.domain.BatchYearDeptProgramSem;
import com.ras.cms.domain.Department;
import com.ras.cms.domain.DepartmentSemSec;

import java.util.List;

public interface BatchYearDeptProgramSemService {


    BatchYearDeptProgramSem findOne(Long id);

    List<BatchYearDeptProgramSem> getAllEntries();

    BatchYearDeptProgramSem saveEntry(BatchYearDeptProgramSem bydps);

    boolean existsEntry(BatchYearDeptProgramSem batchYearDeptProgramSem);

    Long findId(BatchYearDeptProgramSem batchYearDeptProgramSem);

    BatchYearDeptProgramSem findRow(BatchYearDeptProgramSem batchYearDeptProgramSem);

   // Department findDepartment(Long batchYearDeptProgramSemId);

}
