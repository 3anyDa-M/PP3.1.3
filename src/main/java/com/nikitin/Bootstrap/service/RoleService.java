package com.nikitin.Bootstrap.service;

import com.nikitin.Bootstrap.models.Role;
import com.nikitin.Bootstrap.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {
 private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAllRoles() {
        LOGGER.info("Finding all roles");
        return roleRepository.findAll();
    }

    public Set<Role> findRolesByIds(Collection<Long> ids) {
        LOGGER.info("Finding roles by ids {}", ids);
        return new HashSet<>(roleRepository.findAllById(ids));
    }

    public Role findById(Long id) {
        LOGGER.info("Finding role by id: {}", id);
        return roleRepository.findById(id).orElse(null);
    }
}