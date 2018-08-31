package com.ras.cms.service.state;

import com.ras.cms.domain.Role;
import com.ras.cms.domain.State;

import java.util.List;

public interface StateService {
    List<State> findAll();
    State findOne(Long id);
    State saveState(State state);
    void deleteState(Long id);
}