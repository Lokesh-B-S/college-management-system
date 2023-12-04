package com.ras.cms.service.openelectivetype;

import com.ras.cms.repository.OpenElectiveTypeRepository;
import com.ras.cms.domain.OpenElectiveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenElectiveTypeServiceImpl implements OpenElectiveTypeService{

    @Autowired
    OpenElectiveTypeRepository openElectiveTypeRepository;


    public List<OpenElectiveType> findAll(){
        return openElectiveTypeRepository.findAll();
    }

    public OpenElectiveType findOne(Long id) {
        return openElectiveTypeRepository.findById(id).get();
    }

    public OpenElectiveType getOpenElectiveTypeByTypeOfOpenElective(String typeOfOpenElective){
        return openElectiveTypeRepository.findByTypeOfOpenElective(typeOfOpenElective);
    }
    public OpenElectiveType saveOpenElectiveType(OpenElectiveType openElectiveType){
        return openElectiveTypeRepository.save(openElectiveType);
    }

    public void deleteOpenElectiveType(Long id) {
        openElectiveTypeRepository.deleteById(id);
    }





}
