package com.ras.cms.service.batch;

import com.ras.cms.domain.Batch;
import com.ras.cms.domain.Department;
import com.ras.cms.domain.TimeSlotSelection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface BatchService {

    List<Batch> findAll();

    Batch findOne(Long id);
    Batch saveBatch(Batch Batch);

    void deleteBatch(Long id);

    List<Batch> getBatchesByDepartment(Department department);
}
