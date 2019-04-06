package com.ras.cms.service.user;

import com.ras.cms.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findOne(Long id);
    User saveUser(User user);
    void deleteUser(Long id);
}