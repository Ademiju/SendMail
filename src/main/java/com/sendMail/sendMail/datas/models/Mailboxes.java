package com.sendMail.sendMail.datas.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@Document
public class Mailboxes {
    private String ownerId;
    private List<Mailbox> mailboxes;

}
