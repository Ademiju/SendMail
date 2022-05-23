package com.sendMail.sendMail.datas.repositories;

import com.sendMail.sendMail.datas.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
