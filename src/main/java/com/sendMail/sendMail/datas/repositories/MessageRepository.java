package com.sendMail.sendMail.datas.repositories;

import com.sendMail.sendMail.datas.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
