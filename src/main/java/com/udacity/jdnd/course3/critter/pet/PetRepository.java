package com.udacity.jdnd.course3.critter.pet;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends CrudRepository<PetEntity, Long> {

    List<PetEntity> findAllByOwnerId(Long ownerId);
}
