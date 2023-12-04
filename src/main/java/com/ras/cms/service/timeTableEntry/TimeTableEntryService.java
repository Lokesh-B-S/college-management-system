package com.ras.cms.service.timeTableEntry;

import com.ras.cms.domain.*;

import java.util.List;

public interface TimeTableEntryService {

    TimeTableEntry findOne(Long id);

    List<TimeTableEntry> getAllEntries();

    TimeTableEntry saveEntry(TimeTableEntry entry);

   // List<TimeTableEntry> getEntriesByDayAndTimeSlot(Day day, TimeSlot timeSlot);

    //TimeTableEntry getEntriesByDepartmentSemesterSection(Department department, Semester semester, Section section);

    List<TimeTableEntry> getEntriesByDepartmentSemSecId(Long departmentSemSecId);

    boolean doesEntryExist(Long departmentSemSecId, Day day, TimeSlotSelection timeSlot);
    //boolean doesEntryExist(Long departmentSemSecId, Day day, TimeSlotSelection timeSlot, Course course);

    TimeTableEntry getEntry(Long departmentSemSecId, Day day, TimeSlotSelection timeSlot, Course course);

    void deleteTimeTableEntry(Long id);
}
