package com.ras.cms.service.projectTeam;

import com.ras.cms.domain.PEGroup;
import com.ras.cms.domain.ProjectTeam;

import java.util.List;

public interface ProjectTeamService {

    ProjectTeam findOne(Long teamId);


    List<ProjectTeam> findAll();

    ProjectTeam saveGroup(ProjectTeam projectTeam);



}
