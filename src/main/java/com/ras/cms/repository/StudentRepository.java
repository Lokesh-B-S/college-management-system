package com.ras.cms.repository;

import com.ras.cms.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Surya on 13-Jun-18.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByBatchAndDepartmentAndProgram(Batch batch, Department department, Program program);

    boolean existsByBatchAndProgramAndSemester(Batch batch, Program program, Semester semester);

    List<Student> findAllByBatchAndDepartmentAndProgram(Batch batch, Department department, Program program);

    List<Student> findAllByBatchAndProgramAndSemester(Batch batch, Program program, Semester semester);

    //note: even though it is semester.semId, semester.id still working because of @Id annotation making semId the primary key field, so doesnt matter
    //also, the int return type here is the count of rows updated/deleted
    //this query is JPQL(java persistence query language)
    @Modifying
    @Query("UPDATE Student s SET s.academicYear.id = :academicYearId, s.semester.id = :semesterId WHERE s.id IN :studentIds")
    int updateAcademicDetailsForStudents(@Param("studentIds") List<Long> studentIds,
                                         @Param("academicYearId") Long academicYearId,
                                         @Param("semesterId") Long semesterId);


}
