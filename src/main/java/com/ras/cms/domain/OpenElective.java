package com.ras.cms.domain;

import javax.persistence.*;

@Entity
public class OpenElective {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long openElectiveId;
    @ManyToOne
    private Program program;
    @ManyToOne
    private Department department;
    @ManyToOne
    private Semester semester;//No of Semisters

    @ManyToOne
    private Term term;
    @ManyToOne
    private Batch batch;
    @ManyToOne
    private AcademicYear academicYear;
    @ManyToOne
    private TeachingDepartment teachingDepartment;


    private Long courseBatchesCount;
    private String courseType;
    private Long contactHours; // in Years
    private String courseCode;
    private String courseName;

    //    private String courseType;
    private Long lectureCredits;
    private Long tutorialCredits;
    private Long practicalCredits;
    private Long totalCredits;


    //private Long batchYearDeptProgramSemId;
    private Long batchYearSemTermId;

    public OpenElective() {
    }

    public Long getOpenElectiveId() {
        return openElectiveId;
    }

    public void setOpenElectiveId(Long openElectiveId) {
        this.openElectiveId = openElectiveId;
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

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public TeachingDepartment getTeachingDepartment() {
        return teachingDepartment;
    }

    public void setTeachingDepartment(TeachingDepartment teachingDepartment) {
        this.teachingDepartment = teachingDepartment;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public Long getContactHours() {
        return contactHours;
    }

    public void setContactHours(Long contactHours) {
        this.contactHours = contactHours;
    }

    public Long getCourseBatchesCount() {
        return courseBatchesCount;
    }

    public void setCourseBatchesCount(Long courseBatchesCount) {
        this.courseBatchesCount = courseBatchesCount;
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

    public Long getBatchYearSemTermId() {
        return batchYearSemTermId;
    }

    public void setBatchYearSemTermId(Long batchYearSemTermId) {
        this.batchYearSemTermId = batchYearSemTermId;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }
}
