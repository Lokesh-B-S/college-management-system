package com.ras.cms.service.role;

import com.ras.cms.domain.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findOne(Long id);
    Role saveRole(Role role);
    void deleteRole(Long id);
}
