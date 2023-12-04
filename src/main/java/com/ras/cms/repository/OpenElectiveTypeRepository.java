package com.ras.cms.repository;

import com.ras.cms.domain.OpenElectiveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenElectiveTypeRepository extends JpaRepository<OpenElectiveType, Long> {

    OpenElectiveType findByTypeOfOpenElective(String typeOfOpenElective);

}
