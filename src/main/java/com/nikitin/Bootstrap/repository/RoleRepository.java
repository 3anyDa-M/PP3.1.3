package com.nikitin.Bootstrap.repository;


import com.nikitin.Bootstrap.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
//    Role findByName(String name);
    Optional<Role> findByName(String name);

}
