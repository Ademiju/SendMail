package com.sendMail.sendMail.dtos.requests.message;

import com.sendMail.sendMail.datas.models.Message;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class SendMessageRequest {
    private String senderEmail;
    private String receiverEmail;
    private Message message;
}
