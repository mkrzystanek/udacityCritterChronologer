package com.udacity.jdnd.course3.critter.pet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PetRepository extends CrudRepository<PetEntity, Long> {

    List<PetEntity> findAllByOwnerId(Long ownerId);
}
