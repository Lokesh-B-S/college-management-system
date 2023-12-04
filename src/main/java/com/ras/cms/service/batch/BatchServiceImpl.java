package com.ras.cms.service.batch;

import com.ras.cms.repository.BatchRepository;
import com.ras.cms.domain.Batch;
import com.ras.cms.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchServiceImpl implements BatchService{

    @Autowired
    BatchRepository batchRepository;

    public List<Batch> findAll() {
        return batchRepository.findAll();
    }

    public Batch findOne(Long id) {
        return batchRepository.findById(id).get();
    }

    public Batch saveBatch(Batch batch) {
        return batchRepository.save(batch);
    }

    public void deleteBatch(Long id) {
        batchRepository.deleteById(id);
    }

    public List<Batch> getBatchesByDepartment(Department department) {
        return batchRepository.findAllByDepartment(department);
    }
}
