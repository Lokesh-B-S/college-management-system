package com.ras.cms.dao;

import com.ras.cms.domain.OpenElectiveType;

public class OpenElectiveTypeUpdateDAO {


    public enum Action {
        UPDATE,
        DELETE
    }

    private OpenElectiveTypeUpdateDAO.Action action;
    private OpenElectiveType data;

    public OpenElectiveTypeUpdateDAO() {}

    public OpenElectiveTypeUpdateDAO.Action getAction() {
        return action;
    }

    public void setAction(OpenElectiveTypeUpdateDAO.Action action) {
        this.action = action;
    }

    public OpenElectiveType getData() {
        return data;
    }

    public void setData(OpenElectiveType data) {
        this.data = data;
    }
}
