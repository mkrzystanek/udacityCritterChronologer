package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public ScheduleEntity saveSchedule(ScheduleEntity scheduleEntity) {
        return scheduleRepository.save(scheduleEntity);
    }

    public List<ScheduleEntity> getScheduleForEmployee(Long id) {
        return scheduleRepository.findAllByEmployeesId(id);
    }

    public List<ScheduleEntity> getScheduleForPet(Long id) {
        return scheduleRepository.findAllByPetsId(id);
    }

    public List<ScheduleEntity> getScheduleForCustomer(Long id) {
        return scheduleRepository.findAllByPetsOwnerId(id);
    }

    public List<ScheduleEntity> getAllSchedules() {
        return StreamSupport.stream(scheduleRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
