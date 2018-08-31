package com.ras.cms.service.state;

import com.ras.cms.dao.RoleRepository;
import com.ras.cms.dao.StateRepository;
import com.ras.cms.domain.Role;
import com.ras.cms.domain.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    StateRepository stateRepository;

    @Override
    public List<State> findAll() {
        return stateRepository.findAll();
    }

    @Override
    public State findOne(Long id) {
        return stateRepository.findById(id).get();
    }


    @Override
    public State saveState(State state) {
        return stateRepository.save(state);
    }

    @Override
    public void deleteState(Long id) {
        stateRepository.deleteById(id);
    }

}
