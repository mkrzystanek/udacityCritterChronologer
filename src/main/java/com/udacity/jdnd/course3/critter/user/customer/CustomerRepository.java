package com.udacity.jdnd.course3.critter.user.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
}
