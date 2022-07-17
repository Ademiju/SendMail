package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Mailbox;
import com.sendMail.sendMail.datas.models.Mailboxes;
import com.sendMail.sendMail.datas.models.Message;

import java.util.List;

public interface MailboxesService {
    Mailboxes create(String email);
    List<Message> viewAllInbox(String email);
    List<Message> viewAllOutbox(String email);


}
