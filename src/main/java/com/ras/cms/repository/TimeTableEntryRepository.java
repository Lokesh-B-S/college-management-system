package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeTableEntryRepository extends JpaRepository<TimeTableEntry, Long> {
  //  List<TimeTableEntry> findByDayAndTimeSlot(Day day, TimeSlot timeSlot);

    List<TimeTableEntry> findByDepartmentSemSecId(Long departmentSemSecId);

    boolean existsByDepartmentSemSecIdAndDayAndTimeSlot(
            Long departmentSemSecId, Day day, TimeSlotSelection timeSlot);

    TimeTableEntry findByDepartmentSemSecIdAndDayAndTimeSlotAndCourse(
            Long departmentSemSecId, Day day, TimeSlotSelection timeSlot, Course course);


   //TimeTableEntry findByDepartmentSemesterSection(Department department, Semester semester, Section section);

}
