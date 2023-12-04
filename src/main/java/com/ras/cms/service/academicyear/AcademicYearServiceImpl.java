package com.ras.cms.service.academicyear;

import com.ras.cms.repository.AcademicYearRepository;
import com.ras.cms.domain.AcademicYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicYearServiceImpl implements AcademicYearService{

    @Autowired
    AcademicYearRepository academicYearRepository;

    public List<AcademicYear> findAll() {
        return academicYearRepository.findAll();
    }

    public AcademicYear findOne(Long id) {
        return academicYearRepository.findById(id).get();
    }

    public void saveAcademicYear(AcademicYear academicYear) {
//        AcademicYear newAcademicYear = academicYearRepository.findByAcademicYear(academicYear);
//        if(newAcademicYear != null) {
//            newAcademicYear.setAcademicYear(academicYear);
//            academicYearRepository.save(newAcademicYear);
//        }
//        else{
//            AcademicYear newAcademicYear1 = new AcademicYear(academicYear);
//            academicYearRepository.save(newAcademicYear1);
        academicYearRepository.save(academicYear);
//        }


    }

    public void deleteAcademicYear(Long id) {
        academicYearRepository.deleteById(id);
    }

//    public List<AcademicYear> getAcademicYearsByDepartment(Department department) {
//        return academicYearRepository.findAllByDepartment(department);
//    }
}
