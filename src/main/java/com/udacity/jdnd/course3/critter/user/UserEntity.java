package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.EntityInterface;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
// I have chosen a "TABLE_PER_CLASS" inheritance strategy, because child classes have quite a lot of specific fields,
// so this strategy allows to avoid a lot of "null" records in the data base.
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UserEntity implements EntityInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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
