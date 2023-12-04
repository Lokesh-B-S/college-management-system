package com.ras.cms.service.timeTableEntry;

import com.ras.cms.repository.TimeTableEntryRepository;
import com.ras.cms.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeTableEntryServiceImpl implements TimeTableEntryService {

    @Autowired
    TimeTableEntryRepository timeTableEntryRepository;

    @Override
    public TimeTableEntry findOne(Long id) {
        return timeTableEntryRepository.findById(id).get();
    }

    @Override
    public List<TimeTableEntry> getAllEntries() {
        return timeTableEntryRepository.findAll();
    }

    @Override
    public TimeTableEntry saveEntry(TimeTableEntry entry){
       return timeTableEntryRepository.save(entry);
    }
//    @Override
//    public List<TimeTableEntry> getEntriesByDayAndTimeSlot(Day day, TimeSlot timeSlot){
//        return timeTableEntryRepository.findByDayAndTimeSlot(day,timeSlot);
//    }

    @Override
    public List<TimeTableEntry> getEntriesByDepartmentSemSecId(Long departmentSemSecId){
        return timeTableEntryRepository.findByDepartmentSemSecId(departmentSemSecId);
    }

    public boolean doesEntryExist(Long departmentSemSecId, Day day, TimeSlotSelection timeSlot) {
        return timeTableEntryRepository.existsByDepartmentSemSecIdAndDayAndTimeSlot(departmentSemSecId, day, timeSlot);
    }

    //fetching the row based on below parameters
    public TimeTableEntry getEntry(Long departmentSemSecId, Day day, TimeSlotSelection timeSlot, Course course) {
        return timeTableEntryRepository.findByDepartmentSemSecIdAndDayAndTimeSlotAndCourse(departmentSemSecId, day, timeSlot, course);
    }

    @Override
    public void deleteTimeTableEntry(Long id){timeTableEntryRepository.deleteById(id);}





//    public TimeTableEntry getEntriesByDepartmentSemesterSection(Department department, Semester semester, Section section){
//        //return timeTableEntryRepository.findByDepartmentSemesterSection(department,semester,section);
//        return timeTableEntryRepository.findByDepartmentSemesterSection(department,semester,section);
//    }
}
