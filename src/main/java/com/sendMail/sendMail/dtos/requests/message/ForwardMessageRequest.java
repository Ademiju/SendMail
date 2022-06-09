package com.sendMail.sendMail.dtos.requests.message;

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
public class ForwardMessageRequest {
    private String senderEmailAddress;
    private List<String> receiverSendMailAddress;
    private String subject;
    private LocalDateTime dateTime;

    public ForwardMessageRequest(String senderEmailAddress, List<String> receiverSendMailAddress) {
        dateTime = LocalDateTime.now();
        this.senderEmailAddress = senderEmailAddress;
        this.receiverSendMailAddress = receiverSendMailAddress;
    }

    public ForwardMessageRequest(String senderEmailAddress, List<String> receiverSendMailAddress, String subject) {
        dateTime = LocalDateTime.now();
        this.senderEmailAddress = senderEmailAddress;
        this.receiverSendMailAddress = receiverSendMailAddress;
        this.subject = subject;
    }
}
