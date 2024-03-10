package com.ras.cms.service.day;

import com.ras.cms.domain.Day;
import com.ras.cms.domain.EligibleStudent;

import java.util.List;

public interface DayService {

    List<Day> findAll();

    Day findOne(Long id);


    Day saveDay(Day day);
}
