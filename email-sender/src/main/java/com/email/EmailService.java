package com.email;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmailService {

    @GetMapping(path = "/")
    String home();

    @PostMapping(path = "/sendMail")
    EmailResponse sendEmail(@RequestBody EmailRequest request);
}
