package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Mailbox;
import com.sendMail.sendMail.datas.models.MailboxType;
import com.sendMail.sendMail.datas.models.Mailboxes;
import com.sendMail.sendMail.datas.models.Message;
import com.sendMail.sendMail.datas.repositories.MailBoxesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
@SpringBootTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)

class MailboxesServiceImplTest {
    @Autowired
    MailboxesService mailboxesService;
    @Autowired
    MessageServiceImpl messageService;

    @Test
    void mailBoxesCanBeCreated() {
        Mailboxes mailboxes = mailboxesService.create("Ademiju@sendmail.com");
        assertThat(mailboxes.getOwnerId(), is("Ademiju@sendmail.com"));
        assertThat(mailboxes.getMailboxes().size(), is(2));
    }

    @Test
    void multipleMailBoxesCanBeCreated() {
        Mailboxes mailboxes = mailboxesService.create("Ademiju@sendmail.com");
        Mailboxes mailboxes2 = mailboxesService.create("Increase@sendmail.com");
        Mailboxes mailboxes3 = mailboxesService.create("Mike@sendmail.com");
        Mailboxes mailboxes4 = mailboxesService.create("John@sendmail.com");

        assertThat(mailboxes.getOwnerId(), is("Ademiju@sendmail.com"));
        assertThat(mailboxes.getMailboxes().size(), is(2));
        assertThat(mailboxes2.getOwnerId(), is("Increase@sendmail.com"));
        assertThat(mailboxes2.getMailboxes().size(), is(2));
        assertThat(mailboxes3.getOwnerId(), is("Mike@sendmail.com"));
        assertThat(mailboxes3.getMailboxes().size(), is(2));
        assertThat(mailboxes4.getOwnerId(), is("John@sendmail.com"));
        assertThat(mailboxes4.getMailboxes().size(), is(2));
    }

    @Test
    void userCanViewAllInboxMessageTest(){
        mailboxesService.create("Ademiju@sendmail.com");
        mailboxesService.create("Increase@sendmail.com");
        Message messageRequest = new Message("Increase@sendmail.com","Ademiju@sendmail.com","Hi, Increase how are you today?");
        messageService.send(messageRequest);
        messageService.send(messageRequest);
        messageService.send(messageRequest);
        messageService.send(messageRequest);
        List<Message> messages = mailboxesService.viewAllInbox(messageRequest.getReceiverEmailAddress());
        assertThat(messages.size(),is(4));

    }
    @Test
    void userCanViewAllOutboxMessageTest(){
        mailboxesService.create("Ademiju@sendmail.com");
        mailboxesService.create("Increase@sendmail.com");
        Message messageRequest = new Message("Increase@sendmail.com","Ademiju@sendmail.com","Hi, Increase how are you today?");
        messageService.send(messageRequest);
        messageService.send(messageRequest);
        messageService.send(messageRequest);
        messageService.send(messageRequest);
        List<Message> messages = mailboxesService.viewAllOutbox(messageRequest.getSenderEmailAddress());
        assertThat(messages.size(),is(4));

    }
}