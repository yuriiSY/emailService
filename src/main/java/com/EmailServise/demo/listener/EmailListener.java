package com.EmailServise.demo.listener;

import com.EmailServise.demo.message.EmailDTO;
import com.EmailServise.demo.modal.EmailMessage;
import com.EmailServise.demo.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailListener {

    private final EmailServiceImpl emailService;

    @KafkaListener(topics = "${kafka.topic.emailReceived}", groupId = "${spring.kafka.consumer.group-id}")
    public void emailReceived(EmailDTO emailDTO) {
        System.out.println(emailDTO);
        EmailMessage emailMessageData = new EmailMessage();
        emailMessageData.setRecipient(emailDTO.getRecipient());
        emailMessageData.setSubject(emailDTO.getSubject());
        emailMessageData.setText(emailDTO.getText());
        emailService.sendMessage(emailMessageData);
    }
}
