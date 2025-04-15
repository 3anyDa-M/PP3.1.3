package com.nikitin.Bootstrap.service;


import com.nikitin.Bootstrap.models.Role;
import com.nikitin.Bootstrap.models.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> findAllUsers();

     void save(String firstName, String lastName,int age,String email, String password, Set<Role> roles);

    void update(User user);

    void delete(Long id);

    User findById(Long id);

     User findByEmail(String email);

}