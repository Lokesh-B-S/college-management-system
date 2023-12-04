package com.ras.cms.repository;

import com.ras.cms.domain.College;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeRepository extends JpaRepository<College, Long> {
}
