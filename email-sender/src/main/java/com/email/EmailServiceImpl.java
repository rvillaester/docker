package com.email;

import com.email.provider.EmailProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
                        // shuffle the providers so that will add randomness
                        Collections.shuffle(providers);
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
        while(attempt < maxAttempt){
            response = supplier.get();
            if(!Optional.ofNullable(response).orElseGet(EmailResponse::failedResponse).isSucceed()){
                attempt++;
                try {
                    TimeUnit.SECONDS.sleep(retryDelay);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                System.out.println(String.format("Retrying #%s ......", attempt));
            } else break;
        }
        return response;
    }
}
