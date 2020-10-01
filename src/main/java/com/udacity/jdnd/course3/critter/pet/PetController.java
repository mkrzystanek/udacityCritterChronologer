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
    PetService petService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return convertPetEntity(petService.savePet(convertPetDTO(petDTO)));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPetEntity(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.getAllPets()
                .stream()
                .map(this::convertPetEntity).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        throw new UnsupportedOperationException();
    }

    public PetDTO convertPetEntity(PetEntity petEntity) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(petEntity, petDTO);
        return petDTO;
    }

    public PetEntity convertPetDTO(PetDTO petDTO) {
        PetEntity petEntity = new PetEntity();
        BeanUtils.copyProperties(petDTO, petEntity);
        return petEntity;
    }
}
