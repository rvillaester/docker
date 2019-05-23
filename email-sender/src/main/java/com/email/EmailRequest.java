package com.email;

import lombok.Data;

@Data
public class EmailRequest {
    private String sender;
    private String recepient;
    private String subject;
    private String text;
}
