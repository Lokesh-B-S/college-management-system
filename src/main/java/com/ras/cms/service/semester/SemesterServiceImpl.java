package com.ras.cms.service.semester;

import com.ras.cms.repository.SemesterRepository;
import com.ras.cms.domain.Semester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterServiceImpl implements SemesterService{
    @Autowired
    private SemesterRepository semesterRepository;

    public Semester findOne(Long semId) { return semesterRepository.findById(semId).get(); }

    public List<Semester> findAll(){
        return semesterRepository.findAll();
    }

    public Semester saveSemester(Semester semester){
        return semesterRepository.save(semester);
    }

    public Semester findBySem(Long sem){return semesterRepository.findBySem(sem);}

}
