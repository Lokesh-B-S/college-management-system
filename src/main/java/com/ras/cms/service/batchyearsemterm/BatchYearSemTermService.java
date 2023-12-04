package com.ras.cms.service.batchyearsemterm;

import com.ras.cms.domain.BatchYearDeptProgramSem;
import com.ras.cms.domain.BatchYearSemTerm;

import java.util.List;

public interface BatchYearSemTermService {


    BatchYearSemTerm findOne(Long id);

    List<BatchYearSemTerm> getAllEntries();

    BatchYearSemTerm saveEntry(BatchYearSemTerm byst);

    boolean existsEntry(BatchYearSemTerm batchYearSemTerm);

    Long findId(BatchYearSemTerm batchYearSemTerm);

    BatchYearSemTerm findRow(BatchYearSemTerm batchYearSemTerm);

}
