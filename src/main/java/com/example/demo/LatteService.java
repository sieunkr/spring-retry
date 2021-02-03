package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LatteService {

    private final JavaMailSender javaMailSender;
    private final RetryTemplate retryTemplate;

    public void execute() {

        // callback
        retryTemplate.execute(new RetryCallback<Void, RuntimeException>()  {

            @Override
            public Void doWithRetry(RetryContext context) {

                log.info("mail sender...");

                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo("sieunkr@gmail.com");
                simpleMailMessage.setFrom("eddy@gmail.com");

                javaMailSender.send(simpleMailMessage);

                return null;
            }
        });

        // lambda




    }

}
