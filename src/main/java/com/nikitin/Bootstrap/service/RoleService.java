package com.nikitin.Bootstrap.service;

import com.nikitin.Bootstrap.models.Role;
import com.nikitin.Bootstrap.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public Set<Role> findRolesByIds(Collection<Long> ids) {
        return new HashSet<>(roleRepository.findAllById(ids));
    }


    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }
}