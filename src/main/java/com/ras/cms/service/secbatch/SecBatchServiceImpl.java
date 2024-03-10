package com.ras.cms.service.secbatch;

import com.ras.cms.domain.*;
import com.ras.cms.repository.SecBatchRepository;
import com.ras.cms.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecBatchServiceImpl implements SecBatchService{

    @Autowired
    private SecBatchRepository secBatchRepository;

    public SecBatch findOne(Long secBatchId){return secBatchRepository.findById(secBatchId).get();}

    public List<SecBatch> findAll(){return secBatchRepository.findAll();}

    public SecBatch saveSecBatch(SecBatch secBatch){return secBatchRepository.save(secBatch);}

    public List<SecBatch>  getBatchesByAcademicYearAndProgramAndSemesterAndTermAndSection( AcademicYear academicYear, Program program, Semester semester, Term term, Section section){
        return secBatchRepository.findAllByAcademicYearAndProgramAndSemesterAndTermAndSection(academicYear, program, semester, term, section);
}

    public List<SecBatch>  getBatchesByAcademicYearAndProgramAndSemesterAndTerm( AcademicYear academicYear, Program program, Semester semester, Term term){
        return secBatchRepository.findAllByAcademicYearAndProgramAndSemesterAndTerm(academicYear, program, semester, term);
    }


    public void createSecBatch(String batchName, AcademicYear academicYear, Program program, Semester semester, Term term, Section section) {

        SecBatch secBatch;
        // Check if the batch with the given name, academic year, program, semester, term, and section already exists in the database
        if (!secBatchRepository.existsBySecBatchNameAndAcademicYearAndProgramAndSemesterAndTermAndSection(
                batchName, academicYear, program, semester, term, section)) {
            // If not, create a new batch
            secBatch = new SecBatch();
            secBatch.setSecBatchName(batchName);
            secBatch.setAcademicYear(academicYear);
            secBatch.setProgram(program);
            secBatch.setSemester(semester);
            secBatch.setTerm(term);
            secBatch.setSection(section);

            // Add any additional properties or logic as needed

            // Save the batch to the database
            secBatchRepository.save(secBatch);
        }


    }

    // Find or create the default batch
    public SecBatch findOrCreateDefaultBatch(AcademicYear academicYear, Program program, Semester semester, Term term, Section section) {
        // Define the name for the default batch
        String defaultBatchName = section.getSec() + " (Not Applicable)";

        // Try to find the default batch in the database
        SecBatch defaultBatch = secBatchRepository.findBySecBatchNameAndAcademicYearAndProgramAndSemesterAndTermAndSection(
                defaultBatchName, academicYear, program, semester, term, section);

        // If the default batch doesn't exist, create and save a new one
        if (defaultBatch == null) {
            defaultBatch = new SecBatch();
            defaultBatch.setSecBatchName(defaultBatchName);
            defaultBatch.setAcademicYear(academicYear);
            defaultBatch.setProgram(program);
            defaultBatch.setSemester(semester);
            defaultBatch.setTerm(term);
            defaultBatch.setSection(section);

            // Save the new default batch to the database
            secBatchRepository.save(defaultBatch);
        }

        // Return the found or newly created default batch
        return defaultBatch;
    }



}
