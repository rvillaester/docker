package com.email.provider;

import com.email.EmailRequest;
import com.email.EmailResponse;

public interface EmailProvider {

    EmailResponse sendMail(EmailRequest request);
}
