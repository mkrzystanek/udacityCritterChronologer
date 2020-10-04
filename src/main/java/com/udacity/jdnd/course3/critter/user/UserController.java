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
import java.util.List;
import java.util.Objects;
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
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return convertCustomerEntityToCustomerDTO(customerService.saveCustomer(convertCustomerDTOToCustomerEntity(customerDTO)));
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
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
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
            Set<PetEntity> petEntities = customerDTO.getPetIds()
                    .stream()
                    .map(id -> {
                        if (petService.petExists(id)) {
                            return petService.getPet(id);
                        } else {
                            return null;
                        }
                    }).filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            customerEntity.setPets(petEntities);
        }

        return customerEntity;
    }

    private CustomerDTO convertCustomerEntityToCustomerDTO(CustomerEntity customerEntity) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customerEntity, customerDTO);

        if (!CollectionUtils.isEmpty(customerEntity.getPets())) {
            List<Long> petIds = customerEntity.getPets()
                    .stream()
                    .map(PetEntity::getId)
                    .collect(Collectors.toList());
            customerDTO.setPetIds(petIds);
        }

        return customerDTO;
    }
}
