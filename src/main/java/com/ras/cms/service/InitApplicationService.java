package com.ras.cms.service;

import com.ras.cms.RasCmsApplication;
import com.ras.cms.domain.Role;
import com.ras.cms.service.role.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** 
 * Created by Surya on 06-Jun-18.
 */
@Component
@Order(value=1)
public class InitApplicationService implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitApplicationService.class);

    @Autowired
    RoleService roleService;

    public void run(ApplicationArguments applicationArguments) {
        if(roleService.findAll().size() == 0) {
            roleService.saveRole(new Role("SITE_ADMIN", "Site Administrator"));
            roleService.saveRole(new Role("COLLAGE_ADMIN", "Collage Administrator"));
            roleService.saveRole(new Role("COURSE_ADMIN", "Course Administrator"));
            roleService.saveRole(new Role("DEPT_ADMIN", "Department Administrator"));
            roleService.saveRole(new Role("DEPT_HEAD", "Head of Department"));
            roleService.saveRole(new Role("DEPT_LECTURER", "Lecturer"));
            roleService.saveRole(new Role("STUDENT", "Student"));
            roleService.saveRole(new Role("PARENT_GUARDIAN", "Parent or Guardian"));
        }
    }
}
