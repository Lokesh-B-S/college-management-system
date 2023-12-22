package com.ras.cms.dao;

import com.ras.cms.domain.Course;

public class CourseUpdateDAO {

    public enum Action {
        UPDATE,
        DELETE
    }

    private CourseUpdateDAO.Action action;
    private Course data;

    public CourseUpdateDAO() {}

    public CourseUpdateDAO.Action getAction() {
        return action;
    }

    public void setAction(CourseUpdateDAO.Action action) {
        this.action = action;
    }

    public Course getData() {
        return data;
    }

    public void setData(Course data) {
        this.data = data;
    }
}
