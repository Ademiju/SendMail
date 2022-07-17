package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Message;
import com.sendMail.sendMail.datas.repositories.MailBoxesRepository;
import com.sendMail.sendMail.dtos.requests.message.ForwardMessageRequest;
import com.sendMail.sendMail.dtos.requests.message.SendManyMessageRequest;
import com.sendMail.sendMail.dtos.responses.message.SendMessageResponse;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)


class MessageServiceImplTest {
    @Autowired
    MessageServiceImpl messageService;
    @Autowired
    MailBoxesRepository mailBoxesRepository;

    @Test
    void messageCanBeSentTest(){
        Message messageRequest = new Message("Ademiju@sendmail.com","Increase@sendmail.com","Hi, Increase how are you today?");
        assertThat(messageService.send(messageRequest).getAlert(),is("Message Sent"));
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
        List<SendMessageResponse> sendMessageResponses = messageService.sendToMany(sendManyMessageRequest);
        assertThat(sendMessageResponses.get(0).getAlert(),is("Message Sent"));
        assertThat(sendMessageResponses.get(1).getAlert(),is("Message Sent"));
        assertThat(sendMessageResponses.get(2).getAlert(),is("Message Sent"));

    }
    @Test
    void messageToMultipleEmailSendsToOnlyValidEmailTest(){
        List<String> sendMails = new ArrayList<>();
        sendMails.add("Increase@sendmail.com");
        sendMails.add("Mikie@sendmail.com");
        sendMails.add("John@sendmail.com");
        SendManyMessageRequest sendManyMessageRequest = new SendManyMessageRequest("Ademiju@sendmail.com", sendMails,"Hi guys, meeting has been cancelled and rescheduled for 8am tomorrow.Best regards");
        List<SendMessageResponse> sendMessageResponses = messageService.sendToMany(sendManyMessageRequest);
        assertThat(sendMessageResponses.get(0).getAlert(),is("Message Sent"));
        assertThat(sendMessageResponses.get(1).getAlert(),is("Message Not Sent"));
        assertThat(sendMessageResponses.get(2).getAlert(),is("Message Sent"));
        String errorMessage = sendMessageResponses.get(1).getMessage().getErrorMessage();
        assertThat(errorMessage,is("Mikie@sendmail.com does not exist or is inactive"));

    }
    @Test
    void messageReceivedCanBeReadTest(){
        Message messageRequest = new Message("Ademiju@sendmail.com","Increase@sendmail.com","Hi, Increase how are you today?");
        SendMessageResponse sendMessageResponse = messageService.send(messageRequest);
        assertFalse(sendMessageResponse.getMessage().isRead());
        Message message = messageService.read(sendMessageResponse.getMessage().getId());
        assertTrue(message.isRead());

    }
    @Test
    void messageCanBeForwardedToOneReceiverTest(){
        Message messageRequest = new Message("Ademiju@sendmail.com","Increase@sendmail.com","Hi, Increase how are you today?");
        SendMessageResponse sentMessageResponse = messageService.send(messageRequest);
        List<String> sendMails = new ArrayList<>();
        sendMails.add("Bamise@sendmail.com");
//        sendMails.add("Mike@sendmail.com");
//        sendMails.add("John@sendmail.com");
        SendManyMessageRequest sendManyMessageRequest = new SendManyMessageRequest("Ademiju@sendmail.com", sendMails,"Our meeting update");
        List<SendMessageResponse> forwardedMessageResponse = messageService.forward(sendManyMessageRequest,sentMessageResponse.getMessage().getId());
        assertThat(forwardedMessageResponse.get(0).getMessage().getMessageBody(),is(sentMessageResponse.getMessage().getMessageBody()));
    }

    @Test
    void messageCanBeForwardedToManyReceiverTest(){
        Message messageRequest = new Message("Ademiju@sendmail.com","Increase@sendmail.com","Hi, Increase how are you today?");
        SendMessageResponse sentMessageResponse = messageService.send(messageRequest);
        List<String> sendMails = new ArrayList<>();
        sendMails.add("Bamise@sendmail.com");
        sendMails.add("Mike@sendmail.com");
        sendMails.add("John@sendmail.com");
        SendManyMessageRequest sendManyMessageRequest = new SendManyMessageRequest("Ademiju@sendmail.com", sendMails,"Our meeting update");
        List<SendMessageResponse> forwardedMessageResponse = messageService.forward(sendManyMessageRequest,sentMessageResponse.getMessage().getId());
        assertThat(forwardedMessageResponse.get(0).getMessage().getMessageBody(),is(sentMessageResponse.getMessage().getMessageBody()));
        assertThat(forwardedMessageResponse.get(1).getMessage().getMessageBody(),is(sentMessageResponse.getMessage().getMessageBody()));
        assertThat(forwardedMessageResponse.get(2).getMessage().getMessageBody(),is(sentMessageResponse.getMessage().getMessageBody()));
    }
}