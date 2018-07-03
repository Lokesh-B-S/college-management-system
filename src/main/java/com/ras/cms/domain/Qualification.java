package com.ras.cms.domain;

import javax.persistence.Entity;

/**
 * Created by Surya on 12-Jun-18.
 */
//@Entity
public class Qualification {
    private String name;
    private String board;
    private long yearOfPassing;
    private long sgpaOrPercentage;
    private double marksObtained;
    private double totalMarks;
    private double marksInMaths;
    private double marksInScience;

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
}
