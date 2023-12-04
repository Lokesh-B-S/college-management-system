package com.ras.cms.service.timeslotselection;

import com.ras.cms.repository.TimeSlotSelectionRepository;
import com.ras.cms.domain.Department;
import com.ras.cms.domain.TimeSlotSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeSlotSelectionServiceImpl implements TimeSlotSelectionService {

    @Autowired
    private TimeSlotSelectionRepository timeSlotSelectionRepository;

    @Override
    public List<TimeSlotSelection> findAll() {
        return timeSlotSelectionRepository.findAll();
    }

    @Override
    public TimeSlotSelection findOne(Long id) {
        return timeSlotSelectionRepository.findById(id).get();
    }

    @Override
    public TimeSlotSelection saveTimeSlotSelection(TimeSlotSelection timeSlotSelection) {
        return timeSlotSelectionRepository.save(timeSlotSelection);
    }

    @Override
    public void deleteTimeSlotSelection(Long id) {
        timeSlotSelectionRepository.deleteById(id);
    }

    @Override
    public List<TimeSlotSelection> getTimeSlotSelectionsByDepartment(Department department) {
        return timeSlotSelectionRepository.findAllByDepartment(department);
    }
}
