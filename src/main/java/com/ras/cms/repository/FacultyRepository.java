package com.ras.cms.repository;

import com.ras.cms.domain.Batch;
import com.ras.cms.domain.Department;
import com.ras.cms.domain.Faculty;
import com.ras.cms.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    boolean existsByDepartment(Department department);

    List<Faculty> findAllByDepartment(Department department);

}
