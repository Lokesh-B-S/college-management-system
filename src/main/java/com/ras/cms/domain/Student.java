package com.ras.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Surya on 12-Jun-18.
 */
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Date dob;
    private String fatherName;
    private String motherName;
    private String gender;
    private String state;
//    private Address address;
    private Boolean indianNational;
    private String religion;
//    private ArrayList<Qualification> qualifications;
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
    private Date amoutPaidDate;
    private String challanNo;

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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
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

   /* public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }*/

    public Boolean isIndianNational() {
        return indianNational;
    }

    public void setIndianNational(Boolean indianNational) {
        this.indianNational = indianNational;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

   /* public ArrayList<Qualification> getQualifications() {
        return qualifications;
    }

    public void setQualifications(ArrayList<Qualification> qualifications) {
        this.qualifications = qualifications;
    }
*/
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
}
