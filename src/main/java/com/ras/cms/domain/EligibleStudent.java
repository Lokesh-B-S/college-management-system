package com.ras.cms.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class EligibleStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eligibleStudentId;

    @ManyToOne
//    @NotNull
    private Department department;

    @ManyToOne
//    @NotNull
    private Program program;
    @ManyToOne
//    @NotNull
    private AcademicYear academicYear;
    @ManyToOne
//    @NotNull
    private Term term;

    @ManyToOne
    //@NotNull
    private Batch batch;
    @ManyToOne
//    @NotNull
    private Semester semester;
    @ManyToOne
//    @NotNull
    private Section section;

    @ManyToOne
    private SecBatch secBatch;

    @ManyToOne
    private ProjectTeam projectTeam;
    @ManyToOne
    private OEGroup groupOfOpenElective;



    @ManyToOne
    private Course regularCourse;


    @ManyToOne
    private OpenElective openElective;

    @ManyToOne
    private Course professionalElective1;

    @ManyToOne
    private Course professionalElective2;

    @ManyToOne
    private Course professionalElective3;

    @ManyToOne
    private Course professionalElective4;

//    @NotNull
    private String regNumber;
//    @NotNull
    private String usn;

//    @NotNull
    private String name;

    public Long getEligibleStudentId() {
        return eligibleStudentId;
    }

    public void setEligibleStudentId(Long eligibleStudentId) {
        this.eligibleStudentId = eligibleStudentId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
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

    public SecBatch getSecBatch() {
        return secBatch;
    }

    public void setSecBatch(SecBatch secBatch) {
        this.secBatch = secBatch;
    }

    public ProjectTeam getProjectTeam() {
        return projectTeam;
    }

    public void setProjectTeam(ProjectTeam projectTeam) {
        this.projectTeam = projectTeam;
    }

    public OEGroup getGroupOfOpenElective() {
        return groupOfOpenElective;
    }

    public void setGroupOfOpenElective(OEGroup groupOfOpenElective) {
        this.groupOfOpenElective = groupOfOpenElective;
    }

    public OpenElective getOpenElective() {
        return openElective;
    }

    public void setOpenElective(OpenElective openElective) {
        this.openElective = openElective;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Course getRegularCourse() {
        return regularCourse;
    }

    public void setRegularCourse(Course regularCourse) {
        this.regularCourse = regularCourse;
    }

    public Course getProfessionalElective1() {
        return professionalElective1;
    }

    public void setProfessionalElective1(Course professionalElective1) {
        this.professionalElective1 = professionalElective1;
    }

    public Course getProfessionalElective2() {
        return professionalElective2;
    }

    public void setProfessionalElective2(Course professionalElective2) {
        this.professionalElective2 = professionalElective2;
    }

    public Course getProfessionalElective3() {
        return professionalElective3;
    }

    public void setProfessionalElective3(Course professionalElective3) {
        this.professionalElective3 = professionalElective3;
    }

    public Course getProfessionalElective4() {
        return professionalElective4;
    }

    public void setProfessionalElective4(Course professionalElective4) {
        this.professionalElective4 = professionalElective4;
    }
}
