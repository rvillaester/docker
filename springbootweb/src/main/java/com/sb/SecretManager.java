package com.sb;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Component
public class SecretManager {

    private Map<String, String> secretKeyMap = new HashMap<>();

    public String getSecret(String key){
        String value = secretKeyMap.get(key);
        if(value == null){
            try {
                value = getFromSecretFile(key);
            } catch (IOException e) {
                throw new SecretNotFoundException(String.format("Secret not found for %s", key), e);
            }
            secretKeyMap.put(key, value);
        }
        return value;
    }

    private String getFromSecretFile(String key) throws IOException {
        File file = ResourceUtils.getFile(String.format("classpath:secrets/%s", key));
        return new String(Files.readAllBytes(file.toPath()));
    }

}
