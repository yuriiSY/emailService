package com.EmailServise.demo.controller;

import com.EmailServise.demo.message.EmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    @Value("${kafka.topic.emailReceived}")
    private String topic;

    private final KafkaOperations<String, EmailDTO> kafkaOperations;

    @PostMapping("/send")
    public HttpStatus sendEmail() {
        EmailDTO emailDTO = EmailDTO
                .builder()
                .recipient("test@gmail.com")
                .subject("Test Subject")
                .text("Test Content")
                .build();
        kafkaOperations.send(topic, emailDTO);
        return HttpStatus.OK;
    }

}
