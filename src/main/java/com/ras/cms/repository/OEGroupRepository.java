package com.ras.cms.repository;

//import com.ras.cms.domain.Group;
import com.ras.cms.domain.OEGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OEGroupRepository extends JpaRepository<OEGroup, Long> {
}
