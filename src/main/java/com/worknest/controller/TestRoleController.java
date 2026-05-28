package com.worknest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRoleController {

    @GetMapping("/admin/home")
    public String adminHome() {
        return "Welcome Admin";
    }

    @GetMapping("/hr/home")
    public String hrHome() {
        return "Welcome HR";
    }

    @GetMapping("/manager/home")
    public String managerHome() {
        return "Welcome Manager";
    }

    @GetMapping("/employee/home")
    public String employeeHome() {
        return "Welcome Employee";
    }
}