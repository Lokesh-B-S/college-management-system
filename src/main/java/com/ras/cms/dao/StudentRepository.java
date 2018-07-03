package com.ras.cms.dao;

import com.ras.cms.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Surya on 13-Jun-18.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
}
