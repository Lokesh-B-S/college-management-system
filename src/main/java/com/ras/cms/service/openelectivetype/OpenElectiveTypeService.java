package com.ras.cms.service.openelectivetype;

import com.ras.cms.domain.CourseType;
import com.ras.cms.domain.OpenElectiveType;

import java.util.List;

public interface OpenElectiveTypeService {

    List<OpenElectiveType> findAll();

    OpenElectiveType findOne(Long id);

    OpenElectiveType getOpenElectiveTypeByTypeOfOpenElective(String typeOfOpenElective);

    OpenElectiveType saveOpenElectiveType(OpenElectiveType OpenElectiveType);

    void deleteOpenElectiveType(Long id);
}

