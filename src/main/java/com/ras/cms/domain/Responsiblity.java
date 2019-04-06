package com.ras.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Responsiblity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long responsiblityId;
    String respName;

    public Long getResponsiblityId() {
        return responsiblityId;
    }

    public void setResponsiblityId(Long responsiblityId) {
        this.responsiblityId = responsiblityId;
    }

    public String getRespName() {
        return respName;
    }

    public void setRespName(String respName) {
        this.respName = respName;
    }
}
