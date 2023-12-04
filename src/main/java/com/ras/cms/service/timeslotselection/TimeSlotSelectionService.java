package com.ras.cms.service.timeslotselection;

import com.ras.cms.domain.Department;
//import com.ras.cms.domain.TimeSlot;
import com.ras.cms.domain.TimeSlotSelection;

import java.util.List;

public interface TimeSlotSelectionService {

    List<TimeSlotSelection> findAll();

    TimeSlotSelection findOne(Long id);
    TimeSlotSelection saveTimeSlotSelection(TimeSlotSelection TimeSlotSelection);

    void deleteTimeSlotSelection(Long id);

    List<TimeSlotSelection> getTimeSlotSelectionsByDepartment(Department department);
}
