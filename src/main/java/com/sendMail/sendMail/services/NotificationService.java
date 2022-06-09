package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Message;
import com.sendMail.sendMail.datas.models.Notification;

public interface NotificationService {
    Notification create(Message message);
    Notification read(String messageId);
}
