package com.udacity.jdnd.course3.critter.user.customer;

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
}
