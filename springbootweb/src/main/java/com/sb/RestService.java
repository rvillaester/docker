package com.sb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestService {

    @Value("${user.username}")
    private String name;

    @Value("${user.password}")
    private String password;

    @RequestMapping("/")
    public String home() {
        return String.format("Username: %s, Password: %s", name, password);
    }
}
