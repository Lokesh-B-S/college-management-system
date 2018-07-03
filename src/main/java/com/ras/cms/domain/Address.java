package com.ras.cms.domain;

import javax.persistence.Entity;

/**
 * Created by Surya on 12-Jun-18.
 */
//@Entity
public class Address {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public long getPinCode() {
        return pinCode;
    }

    public void setPinCode(long pinCode) {
        this.pinCode = pinCode;
    }

    private String name;
    private String address1;

    public Address(String name, String address1, String address2, String city, String state, long pinCode) {
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        State = state;
        this.pinCode = pinCode;
    }

    private String address2;
    private String city;
    private String State;
    private long pinCode;


}
