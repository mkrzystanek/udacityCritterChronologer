package com.udacity.jdnd.course3.critter.user.employee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findDistinctBySkillsInAndDaysAvailableContaining(Set<EmployeeSkill> skills, DayOfWeek day);
}
