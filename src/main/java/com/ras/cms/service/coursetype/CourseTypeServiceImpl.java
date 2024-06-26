package com.ras.cms.service.coursetype;

import com.ras.cms.domain.OpenElectiveType;
import com.ras.cms.repository.CourseTypeRepository;
import com.ras.cms.domain.CourseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseTypeServiceImpl implements CourseTypeService {

    @Autowired
    CourseTypeRepository courseTypeRepository;


    public List<CourseType> findAll(){
        return courseTypeRepository.findAll();
    }

    public CourseType findOne(Long id) {
        return courseTypeRepository.findById(id).get();
    }

    public CourseType getCourseTypeByTypeOfCourse(String typeOfCourse){
        return courseTypeRepository.findByTypeOfCourse(typeOfCourse);
    }
    public CourseType saveCourseType(CourseType courseType){
        return courseTypeRepository.save(courseType);
    }

    public void deleteCourseType(Long id) {
        courseTypeRepository.deleteById(id);
    }

}
