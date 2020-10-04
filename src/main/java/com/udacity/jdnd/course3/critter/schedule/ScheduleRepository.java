package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findAllByEmployeesId(Long id);
    List<ScheduleEntity> findAllByPetsId(Long id);
    List<ScheduleEntity> findAllByPetsOwnerId(Long id);
}
