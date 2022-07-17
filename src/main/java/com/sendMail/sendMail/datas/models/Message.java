package com.sendMail.sendMail.datas.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@ToString
@Getter
@Setter
@NoArgsConstructor
@Document("sendMailMessages")
public class Message {
    @Id
    private String id;
    private LocalDateTime dateTime = LocalDateTime.now();
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
    }
    public Message(String senderEmailAddress, String receiverEmailAddress,String subject, String messageBody) {
        this.senderEmailAddress = senderEmailAddress;
        this.receiverEmailAddress = receiverEmailAddress;
        this.subject = subject;
        this.messageBody = messageBody;
    }
}
