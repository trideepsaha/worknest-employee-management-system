package com.worknest.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/redirect")
    public String redirectAfterLogin(Authentication authentication) {

        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_ADMIN")) {
            return "redirect:/admin/home";
        } else if (role.equals("ROLE_HR")) {
            return "redirect:/hr/home";
        } else if (role.equals("ROLE_MANAGER")) {
            return "redirect:/manager/home";
        } else {
            return "redirect:/employee/home";
        }
    }
}