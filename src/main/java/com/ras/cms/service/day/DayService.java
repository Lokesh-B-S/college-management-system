package com.ras.cms.service.day;

import com.ras.cms.domain.Day;

import java.util.List;

public interface DayService {

    List<Day> findAll();

    Day saveDay(Day day);
}
