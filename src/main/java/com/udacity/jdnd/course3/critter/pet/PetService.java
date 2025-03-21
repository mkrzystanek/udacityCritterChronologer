package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.CritterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public PetEntity savePet(PetEntity petEntity) {
        return petRepository.save(petEntity);
    }

    public PetEntity getPet(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new CritterException("Failed to find pet with id: " + id));
    }

    public List<PetEntity> getAllPets() {
        return StreamSupport
                .stream(petRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<PetEntity> getPetsByOwner(Long ownerId) {
        return petRepository.findAllByOwnerId(ownerId);
    }

    public boolean petExists(Long id) {
        return petRepository.existsById(id);
    }
}
