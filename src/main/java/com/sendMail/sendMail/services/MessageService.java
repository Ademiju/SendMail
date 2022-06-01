package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Message;
import com.sendMail.sendMail.dtos.requests.message.SendManyMessageRequest;
import com.sendMail.sendMail.dtos.requests.message.SendMessageRequest;

public interface MessageService {
    String send(Message message);
    String sendToMany(SendManyMessageRequest sendManyMessageRequest);
    String forward(SendManyMessageRequest sendManyMessageRequest);
    void read(String email);

}
