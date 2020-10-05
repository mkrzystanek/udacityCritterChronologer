package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.customer.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.customer.CustomerEntity;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeEntity;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeService;
import com.udacity.jdnd.course3.critter.utils.DTOConverterUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;

    @Autowired
    private DTOConverterUtils dtoConverterUtils;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerEntity customerEntity = convertCustomerDTOToCustomerEntity(customerDTO);
        CustomerDTO savedCustomerDTO =  convertCustomerEntityToCustomerDTO(customerService.saveCustomer(customerEntity));
        if (!CollectionUtils.isEmpty(customerDTO.getPetIds())) {
            customerDTO.getPetIds().stream().map(id -> petService.getPet(id)).forEach(pet -> {
                pet.setOwner(customerEntity);
                petService.savePet(pet);
            });
        }
        return savedCustomerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAllCustomers()
                .stream()
                .map(this::convertCustomerEntityToCustomerDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return convertCustomerEntityToCustomerDTO(customerService.getCustomerByPet(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return convertEmployeeEntityToEmployeeDTO(employeeService.saveEmployee(convertEmployeeDTOToEntity(employeeDTO)));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployeeEntityToEmployeeDTO(employeeService.getEmployee(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return employeeService.getEmployeeForService(employeeDTO).stream()
                .map(this::convertEmployeeEntityToEmployeeDTO)
                .collect(Collectors.toList());
    }

    private EmployeeEntity convertEmployeeDTOToEntity(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(employeeDTO, employeeEntity);
        return employeeEntity;
    }

    private EmployeeDTO convertEmployeeEntityToEmployeeDTO(EmployeeEntity employeeEntity) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employeeEntity, employeeDTO);
        return employeeDTO;
    }

    private CustomerEntity convertCustomerDTOToCustomerEntity(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        BeanUtils.copyProperties(customerDTO, customerEntity);

        if (!CollectionUtils.isEmpty(customerDTO.getPetIds())) {
            Collection<PetEntity> petEntities = dtoConverterUtils
                    .convertIdListToEntityCollection(customerDTO.getPetIds(), (id) -> petService.getPet(id));
            customerEntity.setPets(new HashSet<>(petEntities));
        }

        return customerEntity;
    }

    private CustomerDTO convertCustomerEntityToCustomerDTO(CustomerEntity customerEntity) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customerEntity, customerDTO);

        if (!CollectionUtils.isEmpty(customerEntity.getPets())) {
            Collection<Long> petIds = dtoConverterUtils.convertEntitiesToIdList(customerEntity.getPets());
            customerDTO.setPetIds(new ArrayList<>(petIds));
        }

        return customerDTO;
    }
}
