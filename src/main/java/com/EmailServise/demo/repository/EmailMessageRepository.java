package com.EmailServise.demo.repository;

import com.EmailServise.demo.modal.EmailMessage;
import com.EmailServise.demo.modal.EmailStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmailMessageRepository extends CrudRepository<EmailMessage,String> {
    List<EmailMessage> findByStatus(EmailStatus emailStatus);
}

