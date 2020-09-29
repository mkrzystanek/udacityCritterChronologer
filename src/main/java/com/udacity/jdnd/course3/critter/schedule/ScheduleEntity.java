package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

//@Entity
public class ScheduleEntity {

//    @Id
//    @GeneratedValue
//    private Integer id;
//
//    @ManyToMany
//    @JoinTable
//    private List<EmployeeEntity> employees;
//
//    @ManyToMany
//    @JoinTable
//    private List<PetEntity> pets;
//
//    private LocalDate date;
//
//    @ElementCollection
//    @JoinTable(name = "schedule_activities")
//    private Set<EmployeeSkill> activities;
//
//    public ScheduleEntity(Integer id, List<EmployeeEntity> employees, List<PetEntity> pets, LocalDate date, Set<EmployeeSkill> activities) {
//        this.id = id;
//        this.employees = employees;
//        this.pets = pets;
//        this.date = date;
//        this.activities = activities;
//    }
//
//    public ScheduleEntity() {
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public List<EmployeeEntity> getEmployees() {
//        return employees;
//    }
//
//    public void setEmployees(List<EmployeeEntity> employees) {
//        this.employees = employees;
//    }
//
//    public List<PetEntity> getPets() {
//        return pets;
//    }
//
//    public void setPets(List<PetEntity> pets) {
//        this.pets = pets;
//    }
//
//    public LocalDate getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }
//
//    public Set<EmployeeSkill> getActivities() {
//        return activities;
//    }
//
//    public void setActivities(Set<EmployeeSkill> activities) {
//        this.activities = activities;
//    }
}
