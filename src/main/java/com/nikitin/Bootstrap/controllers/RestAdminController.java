package com.nikitin.Bootstrap.controllers;
import com.nikitin.Bootstrap.models.User;
import com.nikitin.Bootstrap.repository.RoleRepository;
import com.nikitin.Bootstrap.repository.UserRepository;
import com.nikitin.Bootstrap.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;


@RestController
@RequestMapping("/admin")
public class RestAdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private  final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public RestAdminController(UserRepository userRepository, RoleRepository roleRepository, UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(user.getRoles().stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role.getName())))
                .collect(Collectors.toSet()));
        return userRepository.save(user);
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            // Обновляем основные поля
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastname(updatedUser.getLastname());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setEmail(updatedUser.getEmail());

            // Обновляем пароль, если он передан, и шифруем его
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword())); // Шифруем новый пароль
            }

            // Обновляем роли
            existingUser.setRoles(updatedUser.getRoles().stream()
                    .map(role -> roleRepository.findByName(role.getName())
                            .orElseThrow(() -> new RuntimeException("Role not found: " + role.getName())))
                    .collect(Collectors.toSet()));

            User savedUser = userRepository.save(existingUser);
            return ResponseEntity.ok(savedUser);
        }).orElse(ResponseEntity.notFound().build());
    }



}
