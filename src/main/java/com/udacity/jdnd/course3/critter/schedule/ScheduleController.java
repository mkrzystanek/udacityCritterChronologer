package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeEntity;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeService;
import com.udacity.jdnd.course3.critter.utils.DTOConverterUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DTOConverterUtils dtoConverterUtils;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return convertScheduleEntityToScheduleDTO(scheduleService.saveSchedule(convertScheduleDTOToScheduleEntity(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules().stream()
                .map(this::convertScheduleEntityToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.getScheduleForPet(petId).stream()
                .map(this::convertScheduleEntityToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getScheduleForEmployee(employeeId).stream()
                .map(this::convertScheduleEntityToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getScheduleForCustomer(customerId).stream()
                .map(this::convertScheduleEntityToScheduleDTO)
                .collect(Collectors.toList());
    }

    private ScheduleDTO convertScheduleEntityToScheduleDTO(ScheduleEntity scheduleEntity) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(scheduleEntity, scheduleDTO);

        if (!CollectionUtils.isEmpty(scheduleEntity.getEmployees())) {
            Collection<Long> employeeIds = dtoConverterUtils.convertEntitiesToIdList(scheduleEntity.getEmployees());
            scheduleDTO.setEmployeeIds(new ArrayList<>(employeeIds));
        }

        if (!CollectionUtils.isEmpty(scheduleEntity.getPets())) {
            Collection<Long> petIds = dtoConverterUtils.convertEntitiesToIdList(scheduleEntity.getPets());
            scheduleDTO.setPetIds(new ArrayList<>(petIds));
        }

        return scheduleDTO;
    }

    private ScheduleEntity convertScheduleDTOToScheduleEntity(ScheduleDTO scheduleDTO) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        BeanUtils.copyProperties(scheduleDTO, scheduleEntity);

        if (!CollectionUtils.isEmpty(scheduleDTO.getEmployeeIds())) {
            Collection<EmployeeEntity> employeeEntities = dtoConverterUtils
                    .convertIdListToEntityCollection(scheduleDTO.getEmployeeIds(), (id) -> employeeService.getEmployee(id));
            scheduleEntity.setEmployees(new ArrayList<>(employeeEntities));
        }

        if (!CollectionUtils.isEmpty(scheduleDTO.getPetIds())) {
            Collection<PetEntity> petEntities = dtoConverterUtils
                    .convertIdListToEntityCollection(scheduleDTO.getPetIds(), (id) -> petService.getPet(id));
            scheduleEntity.setPets(new ArrayList<>(petEntities));
        }

        return scheduleEntity;
    }
}
