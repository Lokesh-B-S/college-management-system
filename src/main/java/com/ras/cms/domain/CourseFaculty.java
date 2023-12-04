package com.ras.cms.domain;

import javax.persistence.*;

@Entity
public class CourseFaculty {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long courseFacultyId;

    @ManyToOne
    private Program program;
    @ManyToOne
    private Department department;
    @ManyToOne
    private Semester semester;
    @ManyToOne
    private Section section;
    @ManyToOne
    private Course course;
    @ManyToOne
    private Faculty faculty;

    public CourseFaculty() {
    }

    public Long getCourseFacultyId() {
        return courseFacultyId;
    }

    public void setCourseFacultyId(Long courseFacultyId) {
        this.courseFacultyId = courseFacultyId;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}

