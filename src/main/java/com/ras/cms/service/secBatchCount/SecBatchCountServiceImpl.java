package com.ras.cms.service.secBatchCount;

import com.ras.cms.domain.*;
import com.ras.cms.repository.SecBatchCountRepository;
import com.ras.cms.service.academicyear.AcademicYearService;
import com.ras.cms.service.program.ProgramService;
import com.ras.cms.service.section.SectionService;
import com.ras.cms.service.semester.SemesterService;
import com.ras.cms.service.termService.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class SecBatchCountServiceImpl implements SecBatchCountService{

    @Autowired
    AcademicYearService academicYearService;

    @Autowired
    SemesterService semesterService;

    @Autowired
    TermService termService;
    @Autowired
    ProgramService programService;

    @Autowired
    SectionService sectionService;
    @Autowired
    SecBatchCountRepository secBatchCountRepository;


    public List<secBatchCount> getBatchCountRowsByAcademicYearAndProgramAndSemesterAndTerm(AcademicYear academicYear, Program program, Semester semester, Term term){
        return secBatchCountRepository.findAllByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);
    }
//    public Integer getCountByAcademicYearAndProgramAndSemesterAndTermAndSection(AcademicYear academicYear, Program program, Semester semester, Term term, Section section){
//    return secBatchCountRepository.findBatchCountByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section);
//}


    public secBatchCount getSecBatchCountRowByAcademicYearAndProgramAndSemesterAndTermAndSection(AcademicYear academicYear, Program program, Semester semester, Term term, Section section){
    return secBatchCountRepository.findByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section);
}


    public void updateOrSaveBatchCount(AcademicYear academicYear, Program program, Semester semester, Term term, Section section, Integer batchCount) {
        // Check if a record exists for the given criteria
        secBatchCount existingBatchCount = secBatchCountRepository.findByAcademicYearAndProgramAndSemesterAndTermAndSection(
                academicYear, program, semester, term, section);

        if (existingBatchCount != null) {
            // If exists, update the batch count
            existingBatchCount.setBatchCount(batchCount);
            secBatchCountRepository.save(existingBatchCount);
        } else {


            // If not exists, create a new record
            secBatchCount newBatchCount = new secBatchCount();
            newBatchCount.setAcademicYear(academicYear);
            newBatchCount.setProgram(program);
            newBatchCount.setSemester(semester);
            newBatchCount.setTerm(term);
            newBatchCount.setSection(section);
            newBatchCount.setBatchCount(batchCount);

            secBatchCountRepository.save(newBatchCount);
        }
    }

}
