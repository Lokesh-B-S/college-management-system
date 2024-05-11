package com.ras.cms.service.projectTeam;

import com.ras.cms.domain.PEGroup;
import com.ras.cms.domain.ProjectTeam;
import com.ras.cms.repository.ProjectTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTeamServiceImpl implements ProjectTeamService{

    @Autowired
    ProjectTeamRepository projectTeamRepository;


    public ProjectTeam findOne(Long teamId){return projectTeamRepository.findById(teamId).get();}
    public List<ProjectTeam> findAll(){return projectTeamRepository.findAll();}

    public ProjectTeam saveGroup(ProjectTeam projectTeam){return projectTeamRepository.save(projectTeam);}

}
