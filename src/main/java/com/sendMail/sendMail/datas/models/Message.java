package com.sendMail.sendMail.datas.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Message {
    @Id
    private String id;
    private String senderEmailAddress;
    private String receiverEmailAddress;
    private String messageBody;
    private LocalDateTime dateTime;
    private boolean isRead;


}
