package com.ras.cms.repository;


import com.ras.cms.domain.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, Long> {

    Semester findBySem(Long sem);
}
