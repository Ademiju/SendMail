package com.sendMail.sendMail.datas.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Message {
    @Id
    private String id;
    private LocalDateTime dateTime;
    private String subject;
    private String senderEmailAddress;
    private String receiverEmailAddress;
    private List<String> receiverSendMailAddress;
    private String messageBody;
    private String errorMessage;
    private boolean isRead;
    private Notification notification;

    public Message(String senderEmailAddress, String receiverEmailAddress, String messageBody) {
        this.senderEmailAddress = senderEmailAddress;
        this.receiverEmailAddress = receiverEmailAddress;
        this.messageBody = messageBody;
        dateTime = LocalDateTime.now();

    }
    public Message(String senderEmailAddress, String receiverEmailAddress,String subject, String messageBody) {
        this.senderEmailAddress = senderEmailAddress;
        this.receiverEmailAddress = receiverEmailAddress;
        this.subject = subject;
        this.messageBody = messageBody;
        dateTime = LocalDateTime.now();

    }
}
