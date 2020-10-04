package com.udacity.jdnd.course3.critter.user.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

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

    public List<EmployeeEntity> getEmployeeForService(EmployeeRequestDTO employeeRequestDTO) {
        Set<EmployeeSkill> skills = employeeRequestDTO.getSkills();
        DayOfWeek dayOfWeek = employeeRequestDTO.getDate().getDayOfWeek();
        List<EmployeeEntity> employees = employeeRepository
                .findDistinctBySkillsInAndDaysAvailableContaining(skills, dayOfWeek);
        return filterEmployeesForSkills(skills, employees);
    }

    private List<EmployeeEntity> filterEmployeesForSkills(Set<EmployeeSkill> requiredSkills, List<EmployeeEntity> employees) {
        return employees.stream()
                .filter(employee -> requiredSkills.stream()
                        .map(skill -> employee.getSkills().contains(skill))
                        .reduce((a, b) -> a && b)
                        .orElse(false))
                .collect(Collectors.toList());
    }
}
