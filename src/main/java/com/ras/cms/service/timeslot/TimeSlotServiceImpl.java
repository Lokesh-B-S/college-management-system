//package com.ras.cms.service.timeslot;
//
//import com.ras.cms.dao.TimeSlotRepository;
//import com.ras.cms.domain.TimeSlot;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class TimeSlotServiceImpl implements TimeSlotService{
//    @Autowired
//    private TimeSlotRepository timeSlotRepository;
//
//    public List<TimeSlot> findAll() {
//        return timeSlotRepository.findAll();
//    }
//
//    public TimeSlot saveTimeSlot(TimeSlot timeSlot) {
//        return timeSlotRepository.save(timeSlot);
//    }
//}
