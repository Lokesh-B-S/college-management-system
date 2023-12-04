package com.ras.cms.repository;

import com.ras.cms.domain.TeachingDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachingDepartmentRepository extends JpaRepository<TeachingDepartment, Long> {

   // List<Batch> findAllByDepartment(Department department);

}
