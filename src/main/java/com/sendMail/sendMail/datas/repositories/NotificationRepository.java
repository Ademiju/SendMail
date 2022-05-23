package com.sendMail.sendMail.datas.repositories;

import ch.qos.logback.core.rolling.helper.MonoTypedConverter;
import com.sendMail.sendMail.datas.models.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification,String> {
}
