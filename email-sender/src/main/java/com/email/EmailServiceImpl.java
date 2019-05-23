package com.email;

import com.email.provider.EmailProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@RestController
public class EmailServiceImpl implements EmailService {

    @Autowired
    private List<? extends EmailProvider> providers;

    @Value("${max.attempt}")
    private int maxAttempt;

    @Value("${retry.delay}")
    private int retryDelay;

    @Override
    public String home() {
        return "I'm Home";
    }

    @Override
    public EmailResponse sendEmail(EmailRequest request) {
         return doRetryable(
                    () -> {
                        EmailResponse response = null;
                        for (EmailProvider provider : providers) {
                            response = provider.sendMail(request);
                            if(response.isSucceed()) break;
                        }
                        return response;
                    }
            );
    }

    private EmailResponse doRetryable(Supplier<EmailResponse> supplier){
        int attempt = 0;
        EmailResponse response = null;
        while(attempt != maxAttempt){
            response = supplier.get();
            if(!response.isSucceed()){
                attempt++;
                try {
                    TimeUnit.SECONDS.sleep(retryDelay);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            } else{
                break;
            }
        }
        return response;
    }
}
