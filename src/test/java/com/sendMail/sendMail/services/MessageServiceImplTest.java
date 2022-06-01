package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Message;
import com.sendMail.sendMail.datas.repositories.MailBoxesRepository;
import com.sendMail.sendMail.dtos.requests.message.SendManyMessageRequest;
import com.sendMail.sendMail.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
@SpringBootTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)

class MessageServiceImplTest {
    @Autowired
    MessageServiceImpl messageService;
    @Autowired
    MailBoxesRepository mailBoxesRepository;

    @Test
    void messageCanBeSentTest(){
        Message message = new Message("Ademiju@sendmail.com","Increase@sendmail.com","Hi, Increase how are you today?");
        assertThat(messageService.send(message),is("Message Sent"));
    }
    @Test
    void sendMessageToIncorrectSendmailThrowsExceptionTest(){
        Message message = new Message("Ademiju@sendmail.com","Increa@sendmail.com","Hi, Increase how are you today?");
        assertThatThrownBy(()->messageService.send(message)).isInstanceOf(UserNotFoundException.class).hasMessage("Receiver's sendmail does not exist");

    }
    @Test
    void messageCanBeSentToMultipleSendMailAddressTest(){
        List<String> sendMails = new ArrayList<>();
        sendMails.add("Increase@sendmail.com");
        sendMails.add("Mike@sendmail.com");
        sendMails.add("John@sendmail.com");
        SendManyMessageRequest sendManyMessageRequest = new SendManyMessageRequest("Ademiju@sendmail.com", sendMails,"Hi guys, meeting starts by 12am");
        assertThat(messageService.sendToMany(sendManyMessageRequest),is("Message Sent"));

    }

}