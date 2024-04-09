package com.ras.cms.service.oegroup;

//import com.ras.cms.domain.Group;
import com.ras.cms.domain.OEGroup;

import java.util.List;

public interface OEGroupService {

    OEGroup findOne(Long groupId);


    List<OEGroup> findAll();

    OEGroup saveGroup(OEGroup group);
}
