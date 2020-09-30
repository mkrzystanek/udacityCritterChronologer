package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.CustomerEntity;
import com.udacity.jdnd.course3.critter.user.customer.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        PetEntity petEntity = petRepository.save(convertPetDTO(petDTO));
        return convertPetEntity(petEntity);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        PetEntity pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Failed to find pet with id = " + petId));
        return convertPetEntity(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return StreamSupport
                .stream(petRepository.findAll().spliterator(), false)
                .map(this::convertPetEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        throw new UnsupportedOperationException();
    }

    public PetDTO convertPetEntity(PetEntity petEntity) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(petEntity, petDTO);
        if (petEntity.getOwner() != null) {
            petDTO.setOwnerId(petEntity.getOwner().getId());
        }
        return petDTO;
    }

    public PetEntity convertPetDTO(PetDTO petDTO) {
        PetEntity petEntity = new PetEntity();
        BeanUtils.copyProperties(petDTO, petEntity);
        if (petDTO.getOwnerId() != null) {

        }
        return petEntity;
    }
}
