package com.ras.cms.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    String facultyIdNumber;

    @ManyToOne
    Department department;

    @NotBlank
    String dob;
    @NotBlank
    String dateOfJoining;
    @NotBlank
    String category;

    String dateOfLeaving;

    @NotBlank
    String gender;
    @NotBlank
    String facultyType;
    @NotBlank
    String name;

    @NotBlank
    String shortName;

    @NotBlank
    String designation;

    @NotBlank
    String highestQualification;

    String specialization;

    int teachingExperience;

    String phoneNumber;

    public Faculty(){ }

    public Faculty(Long id,  String name,  String shortName,String designation, String highestQualification, String specialization, int teachingExperience) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.designation = designation;
        this.highestQualification = highestQualification;
        this.specialization = specialization;
        this.teachingExperience = teachingExperience;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(String highestQualification) {
        this.highestQualification = highestQualification;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getTeachingExperience() {
        return teachingExperience;
    }

    public void setTeachingExperience(int teachingExperience) {
        this.teachingExperience = teachingExperience;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDateOfLeaving() {
        return dateOfLeaving;
    }

    public void setDateOfLeaving(String dateOfLeaving) {
        this.dateOfLeaving = dateOfLeaving;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFacultyType() {
        return facultyType;
    }

    public void setFacultyType(String facultyType) {
        this.facultyType = facultyType;
    }



    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getFacultyIdNumber() {
        return facultyIdNumber;
    }

    public void setFacultyIdNumber(String facultyIdNumber) {
        this.facultyIdNumber = facultyIdNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

