package com.ras.cms.service.batchyearsemterm;

import com.ras.cms.domain.AcademicYear;
import com.ras.cms.domain.Semester;
import com.ras.cms.domain.Term;
import com.ras.cms.repository.BatchYearSemTermRepository;
import com.ras.cms.domain.BatchYearSemTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchYearSemTermServiceImpl implements BatchYearSemTermService{

    @Autowired
    BatchYearSemTermRepository batchYearSemTermRepository;


    @Override
    public BatchYearSemTerm findOne(Long id) {
        return batchYearSemTermRepository.findById(id).get();
    }

    @Override
    public List<BatchYearSemTerm> getAllEntries() {
        return batchYearSemTermRepository.findAll();
    }
    @Override
    public BatchYearSemTerm saveEntry(BatchYearSemTerm byst){
        return batchYearSemTermRepository.save(byst);
    }

    @Override
    public boolean existsEntry(BatchYearSemTerm batchYearSemTerm){
        return batchYearSemTermRepository.existsByBatchAndAcademicYearAndSemesterAndTerm(batchYearSemTerm.getBatch(),batchYearSemTerm.getAcademicYear(),batchYearSemTerm.getSemester(), batchYearSemTerm.getTerm());
    }

    @Override
    public Long findId(BatchYearSemTerm batchYearSemTerm){
        return batchYearSemTermRepository.findIdByBatchAndAcademicYearAndSemesterAndTerm(batchYearSemTerm.getBatch(),batchYearSemTerm.getAcademicYear(),batchYearSemTerm.getSemester(), batchYearSemTerm.getTerm());
    }

    @Override
    public BatchYearSemTerm findRow(BatchYearSemTerm batchYearSemTerm){
        return batchYearSemTermRepository.findByBatchAndAcademicYearAndSemesterAndTerm(batchYearSemTerm.getBatch(),batchYearSemTerm.getAcademicYear(),batchYearSemTerm.getSemester(), batchYearSemTerm.getTerm());
    }

    @Override
    public BatchYearSemTerm getRowByAcademicYearAndSemesterAndTerm(AcademicYear academicYear, Semester semester, Term term){
        return batchYearSemTermRepository.findByAcademicYearAndSemesterAndTerm(academicYear, semester, term);
    }

}
