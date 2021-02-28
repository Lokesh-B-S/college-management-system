package com.ras.cms.service;

import com.ras.cms.domain.Role;
import com.ras.cms.domain.State;
import com.ras.cms.service.role.RoleService;
import com.ras.cms.service.state.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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

    @Autowired
    StateService stateService;

    public void run(ApplicationArguments applicationArguments) {
        if(roleService.findAll().size() == 0) {
            roleService.saveRole(new Role("SITE_ADMIN", "Site Administrator"));
            roleService.saveRole(new Role("College_ADMIN", "College Administrator"));
            roleService.saveRole(new Role("COURSE_ADMIN", "Course Administrator"));
            roleService.saveRole(new Role("DEPT_ADMIN", "Department Administrator"));
            roleService.saveRole(new Role("DEPT_HEAD", "Head of Department"));
            roleService.saveRole(new Role("DEPT_LECTURER", "Lecturer"));
            roleService.saveRole(new Role("STUDENT", "Student"));
            roleService.saveRole(new Role("PARENT_GUARDIAN", "Parent or Guardian"));
        }

        if(stateService.findAll().size()==0){
            stateService.saveState(new State(01L,"Bangalore City"));
            stateService.saveState(new State(30L,"Bangalore Rural"));
            stateService.saveState(new State(6L,"Bijapura"));
            stateService.saveState(new State(2L,"Belagavi"));
            stateService.saveState(new State(4L,"Bellari"));
            stateService.saveState(new State(5L,"Beedar"));
            stateService.saveState(new State(3L,"Bagalkote"));
            stateService.saveState(new State(9L,"Chikkamagaluru"));
            stateService.saveState(new State(8L,"Chikkaballapura"));
            stateService.saveState(new State(7L,"Chamarajanagara"));
            stateService.saveState(new State(10L,"Chitradurga"));
            stateService.saveState(new State(12L,"Dakshina Kannada"));
            stateService.saveState(new State(14L,"Gadaga"));
            stateService.saveState(new State(16L,"Hasana"));
            stateService.saveState(new State(17L,"Haveri"));
            stateService.saveState(new State(11L,"Kodagu"));
            stateService.saveState(new State(19L,"Kolara"));
            stateService.saveState(new State(20L,"Koppala"));
            stateService.saveState(new State(22L,"Mysuru"));
            stateService.saveState(new State(21L,"Mandya"));
            stateService.saveState(new State(23L,"Rayachooru"));
            stateService.saveState(new State(24L,"Ramanagara"));
            stateService.saveState(new State(25L,"Shivamogga"));
            stateService.saveState(new State(26L,"Tumkur"));
        }
    }
}
