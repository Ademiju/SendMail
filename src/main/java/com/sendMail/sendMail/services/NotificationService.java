package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Notification;

public interface NotificationService {
    Notification create(String email);
    void read(String messageId);
}
