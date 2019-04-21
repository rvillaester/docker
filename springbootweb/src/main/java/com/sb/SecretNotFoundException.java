package com.sb;

public class SecretNotFoundException extends RuntimeException {

    public SecretNotFoundException(Exception e){
        super("Secret Not Found", e);
    }

    public SecretNotFoundException(String message, Exception e){
        super(message, e);
    }

    public SecretNotFoundException(String message){
        super(message);
    }
}
