package com.ras.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Surya on 12-Jun-18.
 */
@Entity
public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qualifId;

    private String name;
    private String board;
    private long yearOfPassing;
    private long sgpaOrPercentage;
    private double marksObtained;
    private double totalMarks;
    private double marksInMaths;
    private double marksInScience;
    private String registrationNumber;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Long getQualifId() {
        return qualifId;
    }

    public void setQualifId(Long qualifId) {
        this.qualifId = qualifId;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getYearOfPassing() {
        return yearOfPassing;
    }

    public void setYearOfPassing(long yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
    }

    public long getSgpaOrPercentage() {
        return sgpaOrPercentage;
    }

    public void setSgpaOrPercentage(long sgpaOrPercentage) {
        this.sgpaOrPercentage = sgpaOrPercentage;
    }

    public double getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(double marksObtained) {
        this.marksObtained = marksObtained;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public double getMarksInMaths() {
        return marksInMaths;
    }

    public void setMarksInMaths(double marksInMaths) {
        this.marksInMaths = marksInMaths;
    }

    public double getMarksInScience() {
        return marksInScience;
    }

    public void setMarksInScience(double marksInScience) {
        this.marksInScience = marksInScience;
    }

    public Qualification(String name, long yearOfPassing, long sgpaOrPercentage) {
        this.name = name;
        this.yearOfPassing = yearOfPassing;
        this.sgpaOrPercentage = sgpaOrPercentage;
    }
    public Qualification(String name) {
        this.name = name;
    }

    public Qualification(){

    }
}
