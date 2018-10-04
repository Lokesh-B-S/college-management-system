package com.ras.cms.service.branch;

import com.ras.cms.domain.Branch;

import java.util.List;

public interface BranchService {
    List<Branch> findAll();
    Branch findOne(Long id);
    Branch saveBranch(Branch branch);
    void deleteBranch(Long id);
}