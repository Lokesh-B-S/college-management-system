package com.ras.cms.service.student;

import com.ras.cms.domain.*;
import com.ras.cms.repository.SectionRepository;
import com.ras.cms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Surya on 13-Jun-18.
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findOne(Long id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public boolean existsStudentEntryByBatchAndDepartmentAndProgram(Batch batch, Department department, Program program) {
        return studentRepository.existsByBatchAndDepartmentAndProgram(batch, department, program);
    }

    @Override
    public boolean existsStudentEntryByBatchAndDepartmentAndProgramAndSemester(Batch batch, Department department, Program program, Semester semester) {
        return studentRepository.existsByBatchAndDepartmentAndProgramAndSemester(batch, department, program, semester);
    }

    @Override
    public boolean existsStudentEntryByBatchAndProgramAndSemester(Batch batch, Program program, Semester semester) {
        return studentRepository.existsByBatchAndProgramAndSemester(batch, program, semester);
    }

    @Override
    public List<Student> getStudentsByBatchAndDepartmentAndProgram(Batch batch, Department department, Program program){
        return studentRepository.findAllByBatchAndDepartmentAndProgram(batch, department, program);
    }

    @Override
    public List<Student> getStudentsByBatchAndDepartmentAndProgramAndSemester(Batch batch, Department department, Program program, Semester semester){
        return studentRepository.findAllByBatchAndDepartmentAndProgramAndSemester(batch, department, program, semester);
    }

    @Override
    public List<Student> getStudentsByBatchAndProgramAndSemester(Batch batch, Program program, Semester semester){
        return studentRepository.findAllByBatchAndProgramAndSemester(batch, program, semester);
    }

    @Transactional
    public void bulkAssignAcademicDetails(List<Long> studentIds, Long academicYearId, Long semesterId) {
        studentRepository.updateAcademicDetailsForStudents(studentIds, academicYearId, semesterId);
    }

    public void assignSectionToStudent(Long studentId, Long sectionId) {
        // Fetch the student and section from the database
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid section ID: " + sectionId));

        // Assign the section to the student
        student.setSection(section);
        studentRepository.save(student);
    }


}
