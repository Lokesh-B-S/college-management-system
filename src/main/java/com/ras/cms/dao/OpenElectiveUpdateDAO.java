package com.ras.cms.dao;

import com.ras.cms.domain.Department;
import com.ras.cms.domain.OpenElective;

public class OpenElectiveUpdateDAO {


    public enum Action {
        UPDATE,
        DELETE
    }

    private OpenElectiveUpdateDAO.Action action;
    private OpenElective data;

    public OpenElectiveUpdateDAO() {}

    public OpenElectiveUpdateDAO.Action getAction() {
        return action;
    }

    public void setAction(OpenElectiveUpdateDAO.Action action) {
        this.action = action;
    }

    public OpenElective getData() {
        return data;
    }

    public void setData(OpenElective data) {
        this.data = data;
    }
}
