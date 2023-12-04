package com.ras.cms.repository;

import com.ras.cms.domain.Batch;
import com.ras.cms.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, Long> {

    List<Batch> findAllByDepartment(Department department);

}
