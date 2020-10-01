package com.udacity.jdnd.course3.critter.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
// I have chosen to stick to default inheritance strategy (SINGLE_TABLE) because it provides quick and easy access to
// data. Because this is a practice project, I don't need to save data base space. There isn't any need for not null
// fields in my Entities.
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public UserEntity() {
    }

    public UserEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
