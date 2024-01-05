package com.ras.cms.service.student;

import com.ras.cms.domain.*;

import java.util.List;

/**
 * Created by Surya on 12-Jun-18.
 */
public interface StudentService {
    List<Student> findAll();

    Student findOne(Long id);

    Student saveStudent(Student student);

    void deleteStudent(Long id);

    boolean existsStudentEntryByBatchAndDepartmentAndProgram(Batch batch, Department department, Program program);

    boolean existsStudentEntryByBatchAndProgramAndSemester(Batch batch, Program program, Semester semester);

    List<Student> getStudentsByBatchAndDepartmentAndProgram(Batch batch, Department department, Program program);

    List<Student> getStudentsByBatchAndProgramAndSemester(Batch batch, Program program, Semester semester);

    void bulkAssignAcademicDetails(List<Long> studentIds, Long academicYearId, Long semesterId);

}
