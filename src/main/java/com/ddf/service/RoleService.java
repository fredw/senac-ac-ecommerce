package com.ddf.service;

import com.ddf.domain.Order;
import com.ddf.domain.Role;
import com.ddf.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    public Role get(Long id) {
        return this.roleRepository.findOne(id);
    }

    public Role save(Role role) {
        return this.roleRepository.save(role);
    }
}
