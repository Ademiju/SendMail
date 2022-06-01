package com.sendMail.sendMail.dtos.requests.message;

import com.sendMail.sendMail.datas.models.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SendManyMessageRequest {
    private String senderEmailAddress;
    private List<String> receiverSendMailAddress;
    private String subject;
    private String messageBody;
    private LocalDateTime dateTime;

    public SendManyMessageRequest(String senderEmailAddress, List<String> receiverSendMailAddress, String subject, String messageBody) {
        dateTime = LocalDateTime.now();
        this.senderEmailAddress = senderEmailAddress;
        this.receiverSendMailAddress = receiverSendMailAddress;
        this.subject = subject;
        this.messageBody = messageBody;
    }
    public SendManyMessageRequest(String senderEmailAddress, List<String> receiverSendMailAddress, String messageBody) {
        dateTime = LocalDateTime.now();
        this.senderEmailAddress = senderEmailAddress;
        this.receiverSendMailAddress = receiverSendMailAddress;
        this.messageBody = messageBody;
    }
}


