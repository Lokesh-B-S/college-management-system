package com.ras.cms.dao;

import com.ras.cms.domain.Faculty;
import com.ras.cms.domain.TeachingDepartment;

public class FacultyUpdateDAO {

    public enum Action {
        UPDATE,
        DELETE
    }

    private Action action;
    private Faculty data;

    public FacultyUpdateDAO(){};

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Faculty getData() {
        return data;
    }

    public void setData(Faculty data) {
        this.data = data;
    }
}
