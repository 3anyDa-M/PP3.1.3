package com.nikitin.Bootstrap.controllers;

import com.nikitin.Bootstrap.models.Role;
import com.nikitin.Bootstrap.models.User;
import com.nikitin.Bootstrap.service.RoleService;
import com.nikitin.Bootstrap.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class AdminController {


    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, Authentication authentication) {
        String username = authentication.getName(); // это email
        User currentUser = userService.findByEmail(username);

        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        model.addAttribute("currentUserEmail", username);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("roles", roles);
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("userUp", new User());
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "bootstrap/Admin-page";
    }


    @PostMapping("/admin/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.save(user.getFirstName(), user.getLastname(),
                user.getAge(), user.getEmail(), user.getPassword(), user.getRoles());
        return "redirect:/admin";

    }
    @PostMapping("/admin/{id}")
    public String updateUser(@ModelAttribute("userUp") User user,
                             @PathVariable Long id) {
        user.setId(id);
        Set<Role> roles = user.getRoles().stream()
                .map(role -> roleService.findById(role.getId()))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }


}
