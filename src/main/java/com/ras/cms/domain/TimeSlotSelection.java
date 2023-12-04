package com.ras.cms.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
//@TypeDef(name = "localTime", typeClass = org.hibernate.type.LocalTimeType.class)
public class TimeSlotSelection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Program program;
    @ManyToOne
    private Department department;

    //@ManyToOne
    //@Type(type = "localTime")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    //@ManyToOne
   // @Type(type = "localTime")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    public TimeSlotSelection(){}

    public TimeSlotSelection(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
