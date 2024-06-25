package com.EmailServise.demo.service;

import com.EmailServise.demo.modal.EmailMessage;
import com.EmailServise.demo.modal.EmailStatus;
import com.EmailServise.demo.repository.EmailMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableAsync(proxyTargetClass = true)
@RequiredArgsConstructor
public class EmailServiceImpl {

    private final JavaMailSender mailSender;

    private final EmailMessageRepository emailMessageRepository;

    @Value("${spring.mail.username}")
    private String emailUsername;

    public EmailMessage sendMessage(EmailMessage emailMessage) {
        try {
            send(emailMessage);
            emailMessage.setStatus(EmailStatus.SENT);
            emailMessage.setErrorMessage(null);
        } catch (MailException e) {
            emailMessage.setStatus(EmailStatus.ERROR);
            emailMessage.setErrorMessage(e.getMessage());
        }
        emailMessage.setCount(emailMessage.getCount() + 1);
        emailMessage.setLastSendTime(LocalDateTime.now());
        return emailMessageRepository.save(emailMessage);
    }

    @Async
    protected void send(EmailMessage emailMessage) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailUsername);
        simpleMailMessage.setTo(emailMessage.getRecipient());
        simpleMailMessage.setSubject(emailMessage.getSubject());
        simpleMailMessage.setText(emailMessage.getText());
        mailSender.send(simpleMailMessage);
    }

    @Scheduled(fixedRateString = "${email.retry.interval}")
    public void retryFailedEmails() {
        List<EmailMessage> failedEmails = emailMessageRepository.findByStatus(EmailStatus.ERROR);
        for (EmailMessage emailMessage : failedEmails) {
            sendMessage(emailMessage);
        }
    }

}
