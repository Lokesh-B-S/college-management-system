package com.ras.cms.repository;

import com.ras.cms.domain.PEGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PEGroupRepository extends JpaRepository<PEGroup, Long> {

}
