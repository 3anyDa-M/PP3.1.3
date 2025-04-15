package com.nikitin.Bootstrap.dao;

import com.nikitin.Bootstrap.models.Role;
import com.nikitin.Bootstrap.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface UserDao {

    List<User> getAllUsers();


    void saveUser(String firstName, String lastName, int age,String email,  String password, Set<Role> roles);

    void deleteUserById(Long id);

    void updateUser(User user);

    User findUserById(Long id);


}
