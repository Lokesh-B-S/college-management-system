package com.ras.cms.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Surya on 12-Jun-18.
 */
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private String state;

    public String getNativeCode() {
        return nativeCode;
    }

    public void setNativeCode(String nativeCode) {
        this.nativeCode = nativeCode;
    }

    private String nativeCode;

    @NotNull
    private int pin;
    @NotNull
    private String primaryContactNumber;
    private String secondaryContactNumber;
    @NotNull
    private String primaryEmailAddress;
    private String secondaryEmailAddress;

    public String getIndianNational() {
        return indianNational;
    }

    public void setIndianNational(String indianNational) {
        this.indianNational = indianNational;
    }

    private String indianNational;
    private String religion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Qualification> qualifications;

    private int yearsStudiedInState;
    private Boolean ruralStudent;
    private Boolean SNQCliming;
    private Boolean studiedInKannadaMedium;
    private long anualIncome;
    private String anualIncomeUnit;
    private String category;
    private String nameOfCaste;
    private String specialCategoryCode;
    private long amoutPaid;
    //    private Address address;
    private Date amoutPaidDate;
    private String challanNo;

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

    public Boolean isRuralStudent() {
        return ruralStudent;
    }

    public void setRuralStudent(Boolean ruralStudent) {
        this.ruralStudent = ruralStudent;
    }

    public Boolean isSNQCliming() {
        return SNQCliming;
    }

    public void setSNQCliming(Boolean SNQCliming) {
        this.SNQCliming = SNQCliming;
    }

    public Boolean isStudiedInKannadaMedium() {
        return studiedInKannadaMedium;
    }

    public void setStudiedInKannadaMedium(Boolean studiedInKannadaMedium) {
        this.studiedInKannadaMedium = studiedInKannadaMedium;
    }

    public long getAnualIncome() {
        return anualIncome;
    }

    public void setAnualIncome(long anualIncome) {
        this.anualIncome = anualIncome;
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

    public long getAmoutPaid() {
        return amoutPaid;
    }

    public void setAmoutPaid(long amoutPaid) {
        this.amoutPaid = amoutPaid;
    }

    public Date getAmoutPaidDate() {
        return amoutPaidDate;
    }

    public void setAmoutPaidDate(Date amoutPaidDate) {
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
}
