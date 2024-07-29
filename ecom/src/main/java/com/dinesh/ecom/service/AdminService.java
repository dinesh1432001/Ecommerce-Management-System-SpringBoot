package com.dinesh.ecom.service;

import com.dinesh.ecom.entity.Admin;
import com.dinesh.ecom.reposistory.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository repository;

    public Admin register(Admin admin) {
        return repository.save(admin);
    }

    public Admin findByUsername(String username) {
        return repository.findByUsername(username);
    }
}

