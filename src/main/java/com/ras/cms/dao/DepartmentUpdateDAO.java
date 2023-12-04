package com.ras.cms.dao;

import com.ras.cms.domain.Department;

public class DepartmentUpdateDAO {

    public enum Action {
        UPDATE,
        DELETE
    }

    private Action action;
    private Department data;

    public DepartmentUpdateDAO() {}

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Department getData() {
        return data;
    }

    public void setData(Department data) {
        this.data = data;
    }
}
