package com.nikitin.Bootstrap.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller

public class RoleComtroller {

    @GetMapping("/user")
    public String userPage(Model model, Principal principal) {
        String currentUsername = principal != null ? principal.getName() : "Guest";
        model.addAttribute("currentUser", currentUsername);
        return "role/user";
    }
    @GetMapping("/admin")
    public String adminPage(){
        return "role/admin";
    }
}
