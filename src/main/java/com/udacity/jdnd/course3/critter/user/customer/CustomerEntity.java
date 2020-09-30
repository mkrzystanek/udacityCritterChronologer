package com.udacity.jdnd.course3.critter.user.customer;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.user.UserEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class CustomerEntity extends UserEntity {

    private String phoneNumber;
    private String notes;

    @OneToMany(mappedBy = "owner")
    private Set<PetEntity> pets;

    public CustomerEntity() {
    }

    public CustomerEntity(String phoneNumber, String notes, Set<PetEntity> pets) {
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.pets = pets;
    }

    public CustomerEntity(Long id, String name, String phoneNumber, String notes, Set<PetEntity> pets) {
        super(id, name);
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.pets = pets;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<PetEntity> getPets() {
        return pets;
    }

    public void setPets(Set<PetEntity> pets) {
        this.pets = pets;
    }
}
