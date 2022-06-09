package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Mailboxes;
import com.sendMail.sendMail.datas.models.Message;
import com.sendMail.sendMail.datas.models.Notification;
import com.sendMail.sendMail.datas.repositories.MailBoxesRepository;
import com.sendMail.sendMail.datas.repositories.MessageRepository;
import com.sendMail.sendMail.dtos.requests.message.ForwardMessageRequest;
import com.sendMail.sendMail.dtos.requests.message.SendManyMessageRequest;
import com.sendMail.sendMail.dtos.responses.message.SendMessageResponse;
import com.sendMail.sendMail.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService{
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MailBoxesRepository mailBoxesRepository;
    @Autowired
    NotificationService notificationService;
    @Override
    public SendMessageResponse send(Message message) {

        Mailboxes senderMailboxes = mailBoxesRepository.findById(message.getSenderEmailAddress()).orElseThrow(()-> new UserNotFoundException("Sendmail does not exist"));
        senderMailboxes.getMailboxes().get(1).getMessages().add(message);
        Notification notification = notificationService.create(message);
        message.setNotification(notification);
        mailBoxesRepository.save(senderMailboxes);
        Mailboxes receiverMailboxes = mailBoxesRepository.findById(message.getReceiverEmailAddress()).orElseThrow(()-> new UserNotFoundException("Receiver's sendmail does not exist"));
        receiverMailboxes.getMailboxes().get(0).getMessages().add(message);
        mailBoxesRepository.save(receiverMailboxes);
        messageRepository.save(message);

        SendMessageResponse sendMessageResponse = new SendMessageResponse();
        sendMessageResponse.setMessage(message);
        sendMessageResponse.setNotification(notification);
        sendMessageResponse.setAlert("Message Sent");

        return sendMessageResponse;
    }

    @Override
    public List<SendMessageResponse> sendToMany(SendManyMessageRequest sendManyMessageRequest) {
        boolean isValid = true;
        List<SendMessageResponse> sendMessageResponses = new ArrayList<>();

        for (String receiver:sendManyMessageRequest.getReceiverSendMailAddress()) {
            Optional<Mailboxes> receiverMailBoxes = mailBoxesRepository.findById(receiver);
            if(receiverMailBoxes.isPresent()){
                Message message = new Message();
                modelMapper.map(sendManyMessageRequest,message);
                messageRepository.save(message);
                Notification notification = notificationService.create(message);
                message.setNotification(notification);
                receiverMailBoxes.get().getMailboxes().get(0).getMessages().add(message);
                mailBoxesRepository.save(receiverMailBoxes.get());
                SendMessageResponse sendMessageResponse = new SendMessageResponse();
                sendMessageResponse.setMessage(message);
                sendMessageResponse.setNotification(notification);
                sendMessageResponse.setAlert("Message Sent");
                sendMessageResponses.add(sendMessageResponse);
            }else{
                Message message = new Message();
                modelMapper.map(sendManyMessageRequest,message);
                Mailboxes senderMailboxes = mailBoxesRepository.findById(message.getSenderEmailAddress()).orElseThrow(()-> new UserNotFoundException("Sendmail does not exist"));
                message.setErrorMessage(receiver+" does not exist or is inactive");
                senderMailboxes.getMailboxes().get(1).getMessages().add(message);
                mailBoxesRepository.save(senderMailboxes);
                isValid = false;
                SendMessageResponse sendMessageResponse = new SendMessageResponse();
                sendMessageResponse.setMessage(message);
                sendMessageResponse.setAlert("Message Not Sent");
                sendMessageResponses.add(sendMessageResponse);
                messageRepository.save(message);
            }
        }
        if(isValid){
            Message message = new Message();
            modelMapper.map(sendManyMessageRequest,message);
            messageRepository.save(message);
            Mailboxes senderMailboxes = mailBoxesRepository.findById(message.getSenderEmailAddress()).orElseThrow(()-> new UserNotFoundException("Sendmail does not exist"));
            senderMailboxes.getMailboxes().get(1).getMessages().add(message);
            mailBoxesRepository.save(senderMailboxes);
            SendMessageResponse sendMessageResponse = new SendMessageResponse();
            sendMessageResponse.setMessage(message);
            sendMessageResponse.setAlert("Message Sent");
            sendMessageResponses.add(sendMessageResponse);
        }

        return sendMessageResponses;
    }

    @Override
    public List<SendMessageResponse> forward(SendManyMessageRequest sendManyMessageRequest,String messageId) {
        Message message = read(messageId);

        messageRepository.save(message);
        sendManyMessageRequest.setSubject(message.getSubject());
        sendManyMessageRequest.setMessageBody(message.getMessageBody());
        List<SendMessageResponse> sendMessageResponses;
        sendMessageResponses = sendToMany(sendManyMessageRequest);
        return sendMessageResponses;
    }

    @Override
    public Message read(String messageId) {
        Message message = messageRepository.findById(messageId).get();
        message.setRead(true);
        messageRepository.save(message);
        return message;
    }
}
