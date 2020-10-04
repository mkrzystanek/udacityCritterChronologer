package com.udacity.jdnd.course3.critter.user.customer;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public CustomerEntity saveCustomer(CustomerEntity customerEntity) {
        return customerRepository.save(customerEntity);
    }

    public List<CustomerEntity> getAllCustomers() {
        return StreamSupport
                .stream(customerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public CustomerEntity getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Failed to find customer with id: " + id));
    }

    public boolean customerExists(Long id) {
        return customerRepository.existsById(id);
    }

    public void addPet(PetEntity petEntity) {
        CustomerEntity customerEntity = customerRepository.findById(petEntity.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Failed to find customer!"));
        customerEntity.addPet(petEntity);
        customerRepository.save(customerEntity);
    }
}
