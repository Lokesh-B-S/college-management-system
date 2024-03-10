package com.ras.cms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
public class NewTimeTableEntry {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long timeTableEntryId;

        @ManyToOne
        private Day day;
        @ManyToOne
        private Course course;
        @ManyToOne
        private Faculty faculty;

        private Date startDate;

        private Date endDate;



        private String startTime;

//        @JsonFormat(pattern = "HH:mm")

//        @JsonProperty("end_time")
        private String endTime;


        @ManyToOne
        private AcademicYear academicYear;

        @ManyToOne
        private Department department;

        @ManyToOne
        private Program program;
        @ManyToOne
        private Semester semester;
        @ManyToOne
        private Section section;
        @ManyToOne
        private Term term;

        public Long getTimeTableEntryId() {
                return timeTableEntryId;
        }

        public void setTimeTableEntryId(Long timeTableEntryId) {
                this.timeTableEntryId = timeTableEntryId;
        }

        public Day getDay() {
                return day;
        }

        public void setDay(Day day) {
                this.day = day;
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

        public Department getDepartment() {
                return department;
        }

        public void setDepartment(Department department) {
                this.department = department;
        }

        public AcademicYear getAcademicYear() {
                return academicYear;
        }

        public void setAcademicYear(AcademicYear academicYear) {
                this.academicYear = academicYear;
        }

        public Program getProgram() {
                return program;
        }

        public void setProgram(Program program) {
                this.program = program;
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

        public Term getTerm() {
                return term;
        }

        public void setTerm(Term term) {
                this.term = term;
        }

        public Date getStartDate() {
                return startDate;
        }

        public void setStartDate(Date startDate) {
                this.startDate = startDate;
        }

        public Date getEndDate() {
                return endDate;
        }

        public void setEndDate(Date endDate) {
                this.endDate = endDate;
        }

        public String getStartTime() {
                return startTime;
        }

        public void setStartTime(String startTime) {
                this.startTime = startTime;
        }

        public String getEndTime() {
                return endTime;
        }

        public void setEndTime(String endTime) {
                this.endTime = endTime;
        }


//        public Time getStartTime() {
//                return startTime;
//        }
//
//        public void setStartTime(Time startTime) {
//                this.startTime = startTime;
//        }
//
//        public Time getEndTime() {
//                return endTime;
//        }
//
//        public void setEndTime(Time endTime) {
//                this.endTime = endTime;
//        }

}
