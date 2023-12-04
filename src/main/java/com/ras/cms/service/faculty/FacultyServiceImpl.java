package com.ras.cms.service.faculty;

import com.ras.cms.repository.FacultyRepository;
import com.ras.cms.domain.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService{

    @Autowired
    FacultyRepository facultyRepository;

    @Override
    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }

    @Override
    public Faculty findOne(Long id) {
        return facultyRepository.findById(id).get();
    }

    @Override
    public Faculty saveFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
}
