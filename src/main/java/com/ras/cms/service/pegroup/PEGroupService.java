package com.ras.cms.service.pegroup;

import com.ras.cms.domain.PEGroup;

import java.util.List;

public interface PEGroupService {
    PEGroup findOne(Long groupId);


    List<PEGroup> findAll();

    PEGroup saveGroup(PEGroup group);
    
}
