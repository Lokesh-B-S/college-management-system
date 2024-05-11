package com.ras.cms.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProjectTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    private Long teamNo;

    public ProjectTeam() {
    }

    //constructor needed for initApplicationService
    public ProjectTeam(Long teamNo) {
        this.teamNo = teamNo;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(Long teamNo) {
        this.teamNo = teamNo;
    }
}
