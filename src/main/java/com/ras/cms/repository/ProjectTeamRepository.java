package com.ras.cms.repository;

import com.ras.cms.domain.ProjectTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, Long> {
}
