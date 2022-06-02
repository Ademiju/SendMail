package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Mailboxes;
import com.sendMail.sendMail.datas.models.Message;
import com.sendMail.sendMail.datas.models.Notification;
import com.sendMail.sendMail.datas.repositories.MailBoxesRepository;
import com.sendMail.sendMail.datas.repositories.MessageRepository;
import com.sendMail.sendMail.dtos.requests.message.SendManyMessageRequest;
import com.sendMail.sendMail.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String send(Message message) {

        Mailboxes senderMailboxes = mailBoxesRepository.findById(message.getSenderEmailAddress()).orElseThrow(()-> new UserNotFoundException("Sendmail does not exist"));
        senderMailboxes.getMailboxes().get(1).getMessages().add(message);
        messageRepository.save(message);
        mailBoxesRepository.save(senderMailboxes);
        Notification notification = notificationService.create(message);
        message.setNotification(notification);
        Mailboxes receiverMailboxes = mailBoxesRepository.findById(message.getReceiverEmailAddress()).orElseThrow(()-> new UserNotFoundException("Receiver's sendmail does not exist"));
        receiverMailboxes.getMailboxes().get(0).getMessages().add(message);
        mailBoxesRepository.save(receiverMailboxes);
        return "Message Sent";
    }

    @Override
    public String sendToMany(SendManyMessageRequest sendManyMessageRequest) {
        boolean isValid = true;

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
            }else{
                Message message = new Message();
                modelMapper.map(sendManyMessageRequest,message);
                messageRepository.save(message);
                Mailboxes senderMailboxes = mailBoxesRepository.findById(message.getSenderEmailAddress()).orElseThrow(()-> new UserNotFoundException("Sendmail does not exist"));
                message.setErrorMessage(receiver+" does not exist or is inactive");
                senderMailboxes.getMailboxes().get(1).getMessages().add(message);
                mailBoxesRepository.save(senderMailboxes);
                isValid = false;
            }
        }
        if(isValid){
            Message message = new Message();
            modelMapper.map(sendManyMessageRequest,message);
            messageRepository.save(message);
            Mailboxes senderMailboxes = mailBoxesRepository.findById(message.getSenderEmailAddress()).orElseThrow(()-> new UserNotFoundException("Sendmail does not exist"));
            senderMailboxes.getMailboxes().get(1).getMessages().add(message);
            mailBoxesRepository.save(senderMailboxes);
        }
        return "Message Sent";
    }

    @Override
    public String forward(SendManyMessageRequest sendManyMessageRequest) {
        return null;
    }

    @Override
    public void read(String email) {

    }
}
