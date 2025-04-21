package com.nikitin.Bootstrap.controllers;

import com.nikitin.Bootstrap.models.User;
import com.nikitin.Bootstrap.service.RoleService;
import com.nikitin.Bootstrap.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {


    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }



    @GetMapping("/user")
    public String UserPage(Model model, Authentication authentication) {
        String username = authentication.getName(); // это email
        User currentUser = userService.findByEmail(username);

        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        model.addAttribute("currentUserEmail", username);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("roles", roles);
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "bootstrap/User-page";
    }
}
