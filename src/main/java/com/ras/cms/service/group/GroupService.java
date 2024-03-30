package com.ras.cms.service.group;

//import com.ras.cms.domain.Group;
import com.ras.cms.domain.OEGroup;
import com.ras.cms.domain.Section;

import java.util.List;

public interface GroupService {

    OEGroup findOne(Long groupId);


    List<OEGroup> findAll();

    OEGroup saveGroup(OEGroup group);
}
