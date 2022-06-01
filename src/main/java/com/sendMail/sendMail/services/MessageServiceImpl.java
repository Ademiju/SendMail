package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Mailboxes;
import com.sendMail.sendMail.datas.models.Message;
import com.sendMail.sendMail.datas.repositories.MailBoxesRepository;
import com.sendMail.sendMail.datas.repositories.MessageRepository;
import com.sendMail.sendMail.dtos.requests.message.SendManyMessageRequest;
import com.sendMail.sendMail.dtos.requests.message.SendMessageRequest;
import com.sendMail.sendMail.exceptions.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService{
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MailBoxesRepository mailBoxesRepository;
    @Override
    public String send(Message message) {

        Mailboxes receiverMailboxes = mailBoxesRepository.findById(message.getReceiverEmailAddress()).orElseThrow(()-> new UserNotFoundException("Receiver's sendmail does not exist"));
        receiverMailboxes.getMailboxes().get(0).getMessages().add(message);
        Mailboxes senderMailboxes = mailBoxesRepository.findById(message.getSenderEmailAddress()).orElseThrow(()-> new UserNotFoundException("Sendmail does not exist"));
        senderMailboxes.getMailboxes().get(1).getMessages().add(message);

        messageRepository.save(message);
        mailBoxesRepository.save(senderMailboxes);
        mailBoxesRepository.save(receiverMailboxes);
        return "Message Sent";
    }

    @Override
    public String sendToMany(SendManyMessageRequest sendManyMessageRequest) {
        boolean isValid = true;
        Message message = new Message();
        modelMapper.map(sendManyMessageRequest,message);
        for (String receiver:sendManyMessageRequest.getReceiverSendMailAddress()) {
            Optional<Mailboxes> receiverMailBoxes = mailBoxesRepository.findById(receiver);
            if(receiverMailBoxes.isPresent()){
                receiverMailBoxes.get().getMailboxes().get(0).getMessages().add(message);
                mailBoxesRepository.save(receiverMailBoxes);
            }else{
                Mailboxes senderMailboxes = mailBoxesRepository.findById(message.getSenderEmailAddress()).orElseThrow(()-> new UserNotFoundException("Sendmail does not exist"));
                message.setErrorMessage(receiver+" does not exist or is inactive");
                senderMailboxes.getMailboxes().get(1).getMessages().add(message);
                mailBoxesRepository.save(senderMailboxes);
                isValid = false;
            }
        }
        if(isValid){
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
