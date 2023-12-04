package com.ras.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OpenElectiveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String typeOfOpenElective;

    public OpenElectiveType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeOfOpenElective() {
        return typeOfOpenElective;
    }

    public void setTypeOfOpenElective(String typeOfOpenElective) {
        this.typeOfOpenElective = typeOfOpenElective;
    }
}
