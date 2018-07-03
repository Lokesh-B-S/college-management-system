package com.ras.cms.service;

import com.ras.cms.domain.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

/** 
 * Created by Surya on 06-Jun-18.
 */
public class InitApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitApplicationService.class);

    @Autowired
    StudentService notesService;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeTestData() {
        LOGGER.info("Initialize test data");

        notesService.saveStudent(new Student());
        notesService.saveStudent(new Student());

        LOGGER.info("Initialization completed");
    }
}
