package com.udacity.jdnd.course3.critter.user.employee;

import com.udacity.jdnd.course3.critter.user.UserEntity;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
public class EmployeeEntity extends UserEntity {

    @Column(name = "skill")
    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "employee_skill", joinColumns = @JoinColumn(name = "id"))
    private Set<EmployeeSkill> skills;

    @Column(name = "days_available")
    @ElementCollection(targetClass = DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "employee_days_available", joinColumns = @JoinColumn(name = "id"))
    private Set<DayOfWeek> daysAvailable;

    public EmployeeEntity() {
    }

    public EmployeeEntity(Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable) {
        this.skills = skills;
        this.daysAvailable = daysAvailable;
    }

    public EmployeeEntity(Long id, String name, Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable) {
        super(id, name);
        this.skills = skills;
        this.daysAvailable = daysAvailable;
    }


    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
