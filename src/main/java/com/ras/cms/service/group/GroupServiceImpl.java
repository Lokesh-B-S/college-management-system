package com.ras.cms.service.group;

//import com.ras.cms.domain.Group;
import com.ras.cms.domain.OEGroup;
import com.ras.cms.domain.Section;
import com.ras.cms.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService{

    @Autowired
    GroupRepository groupRepository;

    public OEGroup findOne(Long groupId){return groupRepository.findById(groupId).get();}
    public List<OEGroup> findAll(){return groupRepository.findAll();}

    public OEGroup saveGroup(OEGroup group){return groupRepository.save(group);}


}
