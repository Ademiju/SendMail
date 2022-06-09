package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Message;
import com.sendMail.sendMail.datas.models.Notification;
import com.sendMail.sendMail.datas.repositories.NotificationRepository;
import com.sendMail.sendMail.dtos.requests.message.SendManyMessageRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
@SpringBootTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)


class NotificationServiceImplTest {
    @Autowired
    NotificationService notificationService;
    @Autowired
    MessageServiceImpl messageService;

    @Test
    void notificationCanBeCreatedForWhenMessageIsSentTest(){
        Message message = new Message("Ademiju@sendmail.com","Increase@sendmail.com","Hi, Increase my baby how are you today?");
        messageService.send(message);
        Notification notification = notificationService.create(message);
        assertThat(notification.getId(),is(message.getId()));
        assertThat(notification.getTitle(),is("You have a new mail"));

    }
    @Test
    void notificationCanBeReadTest(){
        Message message = new Message("Ademiju@sendmail.com","Increase@sendmail.com","Hi, Increase my darling");
        messageService.send(message);
        Notification notification = notificationService.read(message.getId());
        assertTrue(notification.isRead());
    }

    @Test
    void notificationIsReceivedByAllRecipientWhenMessageIsSentToManyTest(){
        List<String> sendMails = new ArrayList<>();
        sendMails.add("Increase@sendmail.com");
        sendMails.add("Mike@sendmail.com");
        sendMails.add("John@sendmail.com");
        SendManyMessageRequest sendManyMessageRequest = new SendManyMessageRequest("Ademiju@sendmail.com", sendMails,"Hi guys, meeting starts by 12am and lateness will be severely dealt with");
        messageService.sendToMany(sendManyMessageRequest);

    }

}