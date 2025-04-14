package com.nikitin.Bootstrap.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BootstrapController {
//
//    // общая страница user и admin
//    @GetMapping("/hello")
//    public String protectedEndpoint() {
//        return "bootstrap/1";
//    }


    // общая страница user и admin
    @GetMapping("/login")
    public String protectedEndpoint() {
        return "bootstrap/2";
    }


}
