package com.ras.cms.service.newTimeTable;

import com.ras.cms.domain.*;
import com.ras.cms.repository.NewTimeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewTimeTableServiceImpl implements NewTimeTableService{

    @Autowired
    NewTimeTableRepository newTimeTableRepository;

    public List<NewTimeTableEntry> getTimeTableEntriesByAcademicYearAndProgramAndSemesterAndSectionAndTermAndDay(AcademicYear academicYear, Program program, Semester semester, Section section, Term term, Day day){
        return newTimeTableRepository.findAllByAcademicYearAndProgramAndSemesterAndSectionAndTermAndDay(academicYear, program, semester, section, term, day);
    }

}
