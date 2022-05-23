package com.sendMail.sendMail.datas.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class Mailboxes {
    private String ownerId;
    private List<Mailbox> mailboxes;

}
