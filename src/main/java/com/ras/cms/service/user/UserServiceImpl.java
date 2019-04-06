package com.ras.cms.service.user;

import com.ras.cms.dao.UserRepository;
import com.ras.cms.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findOne(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User saveUser(User collage) {
        return userRepository.save(collage);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}