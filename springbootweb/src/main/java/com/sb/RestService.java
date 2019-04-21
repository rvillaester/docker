package com.sb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestService {

    @Value("${app.version}")
    private String version;

    @Value("${user.username}")
    private String name;

    @Autowired
    private SecretManager secretManager;

    @RequestMapping("/")
    public String home() {
        String password = secretManager.getSecret(Secrets.USER_PASSWORD);
        return String.format("Version: %s, Username: %s, Password: %s", version, name, password);
    }
}
