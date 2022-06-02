package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Message;
import com.sendMail.sendMail.datas.models.Notification;
import com.sendMail.sendMail.datas.repositories.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

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
        Message message = new Message("Ademiju@sendmail.com","Increase@sendmail.com","Hi, Increase how are you today?");
        messageService.send(message);
        Notification notification = notificationService.create(message);
        assertThat(notification.getId(),is(message.getId()));
        assertThat(notification.getTitle(),is("You have a new mail"));

    }
    @Test
    void notificationCanBeReadTest(){

//        notificationService.read(s);
    }

}