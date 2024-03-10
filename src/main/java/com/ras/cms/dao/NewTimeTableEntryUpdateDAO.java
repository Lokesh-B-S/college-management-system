package com.ras.cms.dao;

import com.ras.cms.domain.NewTimeTableEntry;

public class NewTimeTableEntryUpdateDAO {

    public enum Action{
        UPDATE,
        DELETE
    }

    private Action action;

    private NewTimeTableEntry data;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public NewTimeTableEntry getData() {
        return data;
    }

    public void setData(NewTimeTableEntry data) {
        this.data = data;
    }
}
