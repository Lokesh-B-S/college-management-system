package com.ras.cms.dao;


import com.ras.cms.domain.TeachingDepartment;

public class TeachingDepartmentUpdateDAO {

    public enum Action {
        UPDATE,
        DELETE
    }

    private TeachingDepartmentUpdateDAO.Action action;
    private TeachingDepartment data;

    public TeachingDepartmentUpdateDAO() {}

    public TeachingDepartmentUpdateDAO.Action getAction() {
        return action;
    }

    public void setAction(TeachingDepartmentUpdateDAO.Action action) {
        this.action = action;
    }

    public TeachingDepartment getData() {
        return data;
    }

    public void setData(TeachingDepartment data) {
        this.data = data;
    }
}
