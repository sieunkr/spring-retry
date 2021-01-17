package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoffeeService {

    private final JavaMailSender javaMailSender;

    @Retryable(
            value = {MailException.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 1000)
    )
    public void execute(final String name) {

        log.info("mail sender...");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("sieunkr@gmail.com");
        simpleMailMessage.setFrom("eddy@gmail.com");

        javaMailSender.send(simpleMailMessage);
    }

    @Recover
    public void recover(MailException e, String name) {
        log.error("failed : {}" + name);
    }
}
