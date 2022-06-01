package com.sendMail.sendMail.datas.repositories;

import com.sendMail.sendMail.datas.models.Mailboxes;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MailBoxesRepository extends MongoRepository<Mailboxes,String> {
}
