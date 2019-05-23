package com.email.provider;

import com.email.EmailRequest;
import com.email.EmailResponse;
import org.springframework.stereotype.Component;

@Component
public class MailGunEmailProvider implements EmailProvider {

    @Override
    public EmailResponse sendMail(EmailRequest request) {
        System.out.println("Mailgun sending email");
        return new EmailResponse(0, "Mailgun good");
    }
}
