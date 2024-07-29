package com.dinesh.ecom.service;

import com.dinesh.ecom.entity.Customer;
import com.dinesh.ecom.reposistory.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Customer register(Customer customer) {
        return repository.save(customer);
    }

    public Customer findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Customer findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
