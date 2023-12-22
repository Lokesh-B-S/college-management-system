package com.ras.cms.domain;

import com.ras.cms.service.coursetype.CourseTypeService;

import javax.persistence.*;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long courseId;
    @ManyToOne
    private Program program;
    @ManyToOne
    private Department department;
    @ManyToOne
    private Semester semester;//No of Semisters
    @ManyToOne
    private Batch batch;
    @ManyToOne
    private AcademicYear academicYear;

    @ManyToOne
    private Term term;
    @ManyToOne
    private TeachingDepartment teachingDepartment;

    @ManyToOne
    private CourseType courseType;
    private Long courseBatchesCount;
   // private String courseType;
    private Long contactHours; // in Years
    private String courseCode;
    private String courseName;

//    private String courseType;
    private Long lectureCredits;
    private Long tutorialCredits;
    private Long practicalCredits;
    private Long totalCredits;

   // private Long batchYearDeptProgramSemId;

    private Long batchYearSemTermId;

    public Long getBatchYearSemTermId() {
        return batchYearSemTermId;
    }

    public void setBatchYearSemTermId(Long batchYearSemTermId) {
        this.batchYearSemTermId = batchYearSemTermId;
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getContactHours() {
        return contactHours;
    }

    public void setContactHours(Long contactHours) {
        this.contactHours = contactHours;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public Long getCourseBatchesCount() {
        return courseBatchesCount;
    }

    public void setCourseBatchesCount(Long courseBatchesCount) {
        this.courseBatchesCount = courseBatchesCount;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public Long getLectureCredits() {
        return lectureCredits;
    }

    public void setLectureCredits(Long lectureCredits) {
        this.lectureCredits = lectureCredits;
    }

    public Long getTutorialCredits() {
        return tutorialCredits;
    }

    public void setTutorialCredits(Long tutorialCredits) {
        this.tutorialCredits = tutorialCredits;
    }

    public Long getPracticalCredits() {
        return practicalCredits;
    }

    public void setPracticalCredits(Long practicalCredits) {
        this.practicalCredits = practicalCredits;
    }

    public Long getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Long totalCredits) {
        this.totalCredits = totalCredits;
    }

    public TeachingDepartment getTeachingDepartment() {
        return teachingDepartment;
    }

    public void setTeachingDepartment(TeachingDepartment teachingDepartment) {
        this.teachingDepartment = teachingDepartment;
    }

//    public Long getBatchYearDeptProgramSemId() {
//        return batchYearDeptProgramSemId;
//    }
//
//    public void setBatchYearDeptProgramSemId(Long batchYearDeptProgramSemId) {
//        this.batchYearDeptProgramSemId = batchYearDeptProgramSemId;
//    }
}
