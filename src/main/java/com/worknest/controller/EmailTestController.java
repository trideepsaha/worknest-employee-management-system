package com.worknest.controller;

import com.worknest.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-email")
@RequiredArgsConstructor
public class EmailTestController {

    private final EmailService emailService;

    @GetMapping
    public String testEmail() {

        emailService.sendEmail(
                "yourreceiver@gmail.com",
                "WorkNest Test Email",
                "Email integration is working successfully."
        );

        return "Email sent successfully";
    }
}