package com.ras.cms.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Surya on 12-Jun-18.
 */
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Department department;

    @ManyToOne
    @NotNull
    private Program program;
    @ManyToOne
    @NotNull
    private AcademicYear academicYear;
    @ManyToOne
    @NotNull
    private Batch batch;
    @ManyToOne
    @NotNull
    private Semester semester;
    @ManyToOne
    @NotNull
    private Section section;

    @NotNull
    private String dateOfRegistration;

    @NotNull
    private String regNumber;
    @NotNull
    private String usn;

    @NotNull
    private String name;
    @NotNull
    private String dob;
    private String address;

    private String aadhar;
    @NotNull
    private String fatherName;
    @NotNull
    private String gender;
    @NotNull
    private String motherName;
    private String motherTongue;
    private String state;
    private String nativeCode;
    @NotNull
    private int pin;
    @NotNull
    private String primaryContactNumber;
    private String secondaryContactNumber;
    @NotNull
    private String primaryEmailAddress;
    private String secondaryEmailAddress;
    private String indianNational;
    private String religion;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Qualification> qualifications;
    private int yearsStudiedInState;
    private String ruralStudent;
    private String snqCliming;
    private String studiedInKannadaMedium;
    private long annualIncome;
    private String anualIncomeUnit;
    private String category;
    private String nameOfCaste;
    private String specialCategoryCode;
    private String quota;

    private long registrationAmountPaid;
    //    private Address address;
    private String amoutPaidDate;
    private String challanNo;
    private String hydKarCliming;

    public String getHydKarCliming() {
        return hydKarCliming;
    }

    public void setHydKarCliming(String hydKarCliming) {
        this.hydKarCliming = hydKarCliming;
    }

    public String getMotherTongue() {
        return motherTongue;
    }

    public void setMotherTongue(String motherTongue) {
        this.motherTongue = motherTongue;
    }

    public String getNativeCode() {
        return nativeCode;
    }

    public void setNativeCode(String nativeCode) {
        this.nativeCode = nativeCode;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrimaryContactNumber() {
        return primaryContactNumber;
    }

    public void setPrimaryContactNumber(String primaryContactNumber) {
        this.primaryContactNumber = primaryContactNumber;
    }

    public String getSecondaryContactNumber() {
        return secondaryContactNumber;
    }

    public void setSecondaryContactNumber(String secondaryContactNumber) {
        this.secondaryContactNumber = secondaryContactNumber;
    }

    public String getPrimaryEmailAddress() {
        return primaryEmailAddress;
    }

    public void setPrimaryEmailAddress(String primaryEmailAddress) {
        this.primaryEmailAddress = primaryEmailAddress;
    }

    public String getSecondaryEmailAddress() {
        return secondaryEmailAddress;
    }

    public void setSecondaryEmailAddress(String secondaryEmailAddress) {
        this.secondaryEmailAddress = secondaryEmailAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public List<Qualification> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    public int getYearsStudiedInState() {
        return yearsStudiedInState;
    }

    public void setYearsStudiedInState(int yearsStudiedInState) {
        this.yearsStudiedInState = yearsStudiedInState;
    }

    public String getRuralStudent() {
        return ruralStudent;
    }

    public void setRuralStudent(String ruralStudent) {
        this.ruralStudent = ruralStudent;
    }

    public String getSnqCliming() {
        return snqCliming;
    }

    public void setSnqCliming(String snqCliming) {
        this.snqCliming= snqCliming;
    }

    public String getStudiedInKannadaMedium() {
        return studiedInKannadaMedium;
    }

    public void setStudiedInKannadaMedium(String studiedInKannadaMedium) {
        this.studiedInKannadaMedium = studiedInKannadaMedium;
    }

    public long getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(long annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getAnualIncomeUnit() {
        return anualIncomeUnit;
    }

    public void setAnualIncomeUnit(String anualIncomeUnit) {
        this.anualIncomeUnit = anualIncomeUnit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNameOfCaste() {
        return nameOfCaste;
    }

    public void setNameOfCaste(String nameOfCaste) {
        this.nameOfCaste = nameOfCaste;
    }

    public String getSpecialCategoryCode() {
        return specialCategoryCode;
    }

    public void setSpecialCategoryCode(String specialCategoryCode) {
        this.specialCategoryCode = specialCategoryCode;
    }

    public long getRegistrationAmountPaid() {
        return registrationAmountPaid;
    }

    public void setRegistrationAmountPaid(long registrationAmountPaid) {
        this.registrationAmountPaid = registrationAmountPaid;
    }

    public String getAmoutPaidDate() {
        return amoutPaidDate;
    }

    public void setAmoutPaidDate(String amoutPaidDate) {
        this.amoutPaidDate = amoutPaidDate;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(String challanNo) {
        this.challanNo = challanNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean hasQualification(Qualification qualification) {
        for (Qualification qualif : getQualifications()) {
            if (qualif.getName() != null && qualif.getName().equalsIgnoreCase(qualification.getName())) {
                return true;
            }
        }
        return false;
    }

    public String getIndianNational() {
        return indianNational;
    }

    public void setIndianNational(String indianNational) {
        this.indianNational = indianNational;
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

    public String getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(String dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
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

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }
}
