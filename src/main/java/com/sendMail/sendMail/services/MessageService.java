package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Message;
import com.sendMail.sendMail.dtos.requests.message.SendManyMessageRequest;
import com.sendMail.sendMail.dtos.responses.message.SendMessageResponse;

import java.util.List;

public interface MessageService {
    SendMessageResponse send(Message message);
    List<SendMessageResponse> sendToMany(SendManyMessageRequest sendManyMessageRequest);
    List<SendMessageResponse> forward(SendManyMessageRequest sendManyMessageRequest, String messageId);
    Message read(String email);

}
