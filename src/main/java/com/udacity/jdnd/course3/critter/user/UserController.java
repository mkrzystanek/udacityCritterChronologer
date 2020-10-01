package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.user.customer.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.customer.CustomerEntity;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeEntity;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
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
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

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
        throw new UnsupportedOperationException();
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
        return customerEntity;
    }

    private CustomerDTO convertCustomerEntityToCustomerDTO(CustomerEntity customerEntity) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customerEntity, customerDTO);
        return customerDTO;
    }
}
