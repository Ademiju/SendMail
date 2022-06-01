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


import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
//    @Test
//    void senderMessageIsDeliveredToTheInboxOfReceiverTest(){
//        Mailboxes mailboxes = mailboxesService.create("Ademiju@sendmail.com");
//        Mailboxes mailboxes2 = mailboxesService.create("Increase@sendmail.com");
//        Message message = new Message(mailboxes2.getOwnerId(),mailboxes.getOwnerId(),"Hi, Miju What do you want for dinner?");
//        messageService.send(message);
//        assertThat(mailboxes.getMailboxes().get(0).getType(),is(MailboxType.INBOX));
//        assertThat(mailboxes2.getMailboxes().get(1).getType(),is(MailboxType.SENT));
//        assertThat(mailboxes.getMailboxes().get(0).getMessages().get(0).getReceiverEmailAddress(),is("Increase@sendmail.com"));
//        assertThat(mailboxes.getMailboxes().get(0).getMessages().get(0).getMessageBody(),is("Hi, Miju What do you want for dinner?"));
//        assertThat(mailboxes.getMailboxes().get(0).getMessages().size(),is(1));
//        assertThat(mailboxes2.getMailboxes().get(1).getMessages().size(),is(1));
//}
}