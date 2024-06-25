package com.EmailServise.demo.service;

import com.EmailServise.demo.modal.EmailMessage;

public interface EmailService {

     EmailMessage sendMessage(EmailMessage emailMessage);
}
