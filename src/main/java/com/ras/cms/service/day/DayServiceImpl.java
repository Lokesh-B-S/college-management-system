package com.ras.cms.service.day;

import com.ras.cms.repository.DayRepository;
import com.ras.cms.domain.Day;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayServiceImpl implements DayService{

    @Autowired
    private DayRepository dayRepository;

    public List<Day> findAll() {
        return dayRepository.findAll();
    }

    public Day saveDay(Day day) {
        return dayRepository.save(day);
    }
}
