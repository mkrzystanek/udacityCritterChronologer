package com.udacity.jdnd.course3.critter.user.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeEntity saveEmployee(EmployeeEntity employeeEntity) {
        return employeeRepository.save(employeeEntity);
    }

    public EmployeeEntity getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Failed to find employee with id: " + id));
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        EmployeeEntity employeeEntity = getEmployee(employeeId);
        employeeEntity.setDaysAvailable(daysAvailable);
        employeeRepository.save(employeeEntity);
    }
}
