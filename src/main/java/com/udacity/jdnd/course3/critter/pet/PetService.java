package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    public PetEntity savePet(PetEntity petEntity) {
        return petRepository.save(petEntity);
    }

    public PetEntity getPet(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Failed to find pet with id: " + id));
    }

    public List<PetEntity> getAllPets() {
        return StreamSupport
                .stream(petRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
