package com.ras.cms.service;

import com.ras.cms.domain.*;
import com.ras.cms.service.coursetype.CourseTypeService;
import com.ras.cms.service.day.DayService;
import com.ras.cms.service.role.RoleService;
import com.ras.cms.service.section.SectionService;
import com.ras.cms.service.semester.SemesterService;
import com.ras.cms.service.state.StateService;
//import com.ras.cms.service.timeslot.TimeSlotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

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

    @Autowired
    private DayService dayService;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private CourseTypeService courseTypeService;

//    @Autowired
//    private TimeSlotService timeSlotService;
    public void run(ApplicationArguments applicationArguments) {
        // Log an info message
        LOGGER.info("Initializing application...");

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

        if (dayService.findAll().isEmpty()) {
            dayService.saveDay(new Day("Monday"));
            dayService.saveDay(new Day("Tuesday"));
            dayService.saveDay(new Day("Wednesday"));
            dayService.saveDay(new Day("Thursday"));
            dayService.saveDay(new Day("Friday"));
            dayService.saveDay(new Day("Saturday"));
            dayService.saveDay(new Day("Sunday"));


            // ... Continue adding days as needed
        }

//        if (timeSlotService.findAll().isEmpty()) {
//            timeSlotService.saveTimeSlot(new TimeSlot(LocalTime.parse("09:00"), LocalTime.parse("10:00")));
//            timeSlotService.saveTimeSlot(new TimeSlot(LocalTime.parse("10:00"), LocalTime.parse("11:00")));
//            timeSlotService.saveTimeSlot(new TimeSlot(LocalTime.parse("11:10"), LocalTime.parse("12:10")));
//            timeSlotService.saveTimeSlot(new TimeSlot(LocalTime.parse("12:10"), LocalTime.parse("01:10")));
//            timeSlotService.saveTimeSlot(new TimeSlot(LocalTime.parse("01:50"), LocalTime.parse("02:50")));
//            timeSlotService.saveTimeSlot(new TimeSlot(LocalTime.parse("02:50"), LocalTime.parse("03:50")));
//            timeSlotService.saveTimeSlot(new TimeSlot(LocalTime.parse("03:50"), LocalTime.parse("04:50")));
//
//            // ... Continue adding time slots as needed
//        }

        if(semesterService.findAll().isEmpty()){
            semesterService.saveSemester(new Semester(1L));
            semesterService.saveSemester(new Semester(2L));
            semesterService.saveSemester(new Semester(3L));
            semesterService.saveSemester(new Semester(4L));
            semesterService.saveSemester(new Semester(5L));
            semesterService.saveSemester(new Semester(6L));
            semesterService.saveSemester(new Semester(7L));
            semesterService.saveSemester(new Semester(8L));
        }

        if(sectionService.findAll().isEmpty()){
            sectionService.saveSection(new Section("A"));
            sectionService.saveSection(new Section("B"));
            sectionService.saveSection(new Section("C"));
            sectionService.saveSection(new Section("D"));
            sectionService.saveSection(new Section("E"));
            sectionService.saveSection(new Section("F"));
            sectionService.saveSection(new Section("G"));
            sectionService.saveSection(new Section("H"));
        }

        if(courseTypeService.findAll().isEmpty()){
            courseTypeService.saveCourseType(new CourseType("Theory"));
            courseTypeService.saveCourseType(new CourseType("Lab"));
            courseTypeService.saveCourseType(new CourseType("Professional Elective 1"));
            courseTypeService.saveCourseType(new CourseType("Professional Elective 2"));
            courseTypeService.saveCourseType(new CourseType("Open Elective 1"));
            courseTypeService.saveCourseType(new CourseType("Open Elective 2"));
        }


        // Log a message when initialization is complete
        LOGGER.info("Initialization complete.");
    }
}
