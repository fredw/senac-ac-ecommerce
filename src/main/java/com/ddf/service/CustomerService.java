package com.ddf.service;

import com.ddf.domain.*;
import com.ddf.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, RoleRepository roleRepository) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
    }

    public void save(Customer customer) {
        customer.getUser().setRole(this.roleRepository.findOne(Role.CUSTOMER));
        this.customerRepository.save(customer);
    }
}
