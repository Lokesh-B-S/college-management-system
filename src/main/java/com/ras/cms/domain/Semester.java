package com.ras.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long semId;

    private Long sem;

    public Semester(){

    }
    public Semester(Long sem) {
        this.sem = sem;
    }

    public Long getSemId() {
        return semId;
    }

    public void setSemId(Long semId) {
        this.semId = semId;
    }

    public Long getSem() {
        return sem;
    }

    public void setSem(Long sem) {
        this.sem = sem;
    }
}
