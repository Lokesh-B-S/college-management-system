package com.ras.cms.service.pegroup;

import com.ras.cms.domain.PEGroup;
import com.ras.cms.repository.PEGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PEGroupServiceImpl implements PEGroupService{

    @Autowired
    PEGroupRepository pegroupRepository;

    public PEGroup findOne(Long groupId){return pegroupRepository.findById(groupId).get();}
    public List<PEGroup> findAll(){return pegroupRepository.findAll();}

    public PEGroup saveGroup(PEGroup group){return pegroupRepository.save(group);}

}
