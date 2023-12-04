package com.ras.cms.repository;

import com.ras.cms.domain.Department;
import com.ras.cms.domain.TimeSlotSelection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeSlotSelectionRepository extends JpaRepository<TimeSlotSelection, Long> {

    List<TimeSlotSelection> findAllByDepartment(Department department);
}
