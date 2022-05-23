package com.sendMail.sendMail.datas.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
public class Mailbox {
    private MailboxType type;
    private List<Message> messages;

}
