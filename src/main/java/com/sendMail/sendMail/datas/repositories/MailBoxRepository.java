package com.sendMail.sendMail.datas.repositories;

import com.sendMail.sendMail.datas.models.Mailbox;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MailBoxRepository extends MongoRepository<Mailbox, String > {
}
