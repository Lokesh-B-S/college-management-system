package com.ras.cms.dao;

import com.ras.cms.domain.Program;

public class ProgramUpdateDAO {

    public enum Action {
        UPDATE,
        DELETE
    }

    private Action action;
    private Program data;

    public ProgramUpdateDAO() {}

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Program getData() {
        return data;
    }

    public void setData(Program data) {
        this.data = data;
    }
}
