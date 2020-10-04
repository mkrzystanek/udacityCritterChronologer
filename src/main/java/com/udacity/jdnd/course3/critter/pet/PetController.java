package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        PetEntity petEntity = petService.savePet(convertPetDTOToPetEntity(petDTO));
        PetDTO savedPet = convertPetEntityToPetDTO(petEntity);
        customerService.addPet(petEntity);
        return savedPet;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPetEntityToPetDTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.getAllPets()
                .stream()
                .map(this::convertPetEntityToPetDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getPetsByOwner(ownerId)
                .stream()
                .map(this::convertPetEntityToPetDTO)
                .collect(Collectors.toList());
    }

    public PetDTO convertPetEntityToPetDTO(PetEntity petEntity) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(petEntity, petDTO);
        if (petEntity.getOwner() != null) {
            petDTO.setOwnerId(petEntity.getOwner().getId());
        }
        return petDTO;
    }

    public PetEntity convertPetDTOToPetEntity(PetDTO petDTO) {
        PetEntity petEntity = new PetEntity();
        BeanUtils.copyProperties(petDTO, petEntity);
        if (customerService.customerExists(petDTO.getOwnerId())) {
            petEntity.setOwner(customerService.getCustomer(petDTO.getOwnerId()));
        }
        return petEntity;
    }
}
