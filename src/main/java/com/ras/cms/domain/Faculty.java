package com.ras.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}

