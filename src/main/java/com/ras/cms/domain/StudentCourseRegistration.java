package com.ras.cms.domain;

import javax.persistence.*;

@Entity
public class StudentCourseRegistration {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
//        @JoinColumn(name = "student_id")
        private EligibleStudent eligibleStudent;

        @ManyToOne
//        @JoinColumn(name = "course_id")
                //for regular and professional electives 1 and 2
        private Course course;

        @ManyToOne
        private OpenElective openElective;


        private String courseType;

        @ManyToOne
        private AcademicYear courseYear;

        @ManyToOne
        private Semester courseSemester;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EligibleStudent getEligibleStudent() {
        return eligibleStudent;
    }

    public void setEligibleStudent(EligibleStudent eligibleStudent) {
        this.eligibleStudent = eligibleStudent;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public OpenElective getOpenElective() {
        return openElective;
    }

    public void setOpenElective(OpenElective openElective) {
        this.openElective = openElective;
    }

    public AcademicYear getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(AcademicYear courseYear) {
        this.courseYear = courseYear;
    }

    public Semester getCourseSemester() {
        return courseSemester;
    }

    public void setCourseSemester(Semester courseSemester) {
        this.courseSemester = courseSemester;
    }
}
