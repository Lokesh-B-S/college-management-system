package com.ras.cms.domain;

import javax.persistence.*;

@Entity
public class TimeTableEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Day day;

    @ManyToOne
    private TimeSlotSelection timeSlot;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Department department;
    @ManyToOne
    private Semester semester;
    @ManyToOne
    private Section section;

    private Long departmentSemSecId;

    public TimeTableEntry(){

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public TimeSlotSelection getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlotSelection timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Course getCourse() {
        return course;
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

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getDepartmentSemSecId() {
        return departmentSemSecId;
    }

    public void setDepartmentSemSecId(Long departmentSemSecId) {
        this.departmentSemSecId = departmentSemSecId;
    }
}
