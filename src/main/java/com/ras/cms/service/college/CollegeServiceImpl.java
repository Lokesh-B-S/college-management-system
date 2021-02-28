package com.ras.cms.service.college;

import com.ras.cms.dao.CollegeRepository;
import com.ras.cms.domain.College;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeServiceImpl implements CollegeService {

    @Autowired
    CollegeRepository CollegeRepository;

    @Override
    public List<College> findAll() {
        return CollegeRepository.findAll();
    }

    @Override
    public College findOne(Long id) {
        return CollegeRepository.findById(id).get();
    }

    @Override
    public College saveCollege(College College) {
        return CollegeRepository.save(College);
    }

    @Override
    public void deleteCollege(Long id) {
        CollegeRepository.deleteById(id);
    }
}