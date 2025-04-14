package com.nikitin.Bootstrap.controllers;

import com.nikitin.Bootstrap.models.Role;
import com.nikitin.Bootstrap.models.User;
import com.nikitin.Bootstrap.service.RoleService;
import com.nikitin.Bootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private final UserService userService;
    private final RoleService roleService;
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAllUsers(Model model, Principal principal) {
        String currentUsername = principal.getName();
        model.addAttribute("currentUser", currentUsername);
        model.addAttribute("allUsers",userService.findAllUsers());
        return "crudUser/allUsers";
    }
    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userService.save(user.getUsername(), user.getPassword(), user.getRoles());
        return "redirect:/admin/users";
    }
    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user",new User());
        model.addAttribute("users",roleService.findAllRoles());
        return "crudUser/newUser";
    }

    @GetMapping("/edit")
    public String editUser(Model model,@RequestParam Long id) {
        User user = userService.findById(id);
        model.addAttribute("userUp",user);
        return "crudUser/editUser";
    }

    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute("userUp") User user,
                             @PathVariable Long id) {
        user.setId(id);
        Set<Role> roles = user.getRoles().stream()
                .map(role -> roleService.findById(role.getId()))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userService.update(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam int id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
