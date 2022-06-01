package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.Mailbox;
import com.sendMail.sendMail.datas.models.Mailboxes;
import com.sendMail.sendMail.dtos.requests.mailboxes.MailboxRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MailboxesService {
    Mailboxes create(String email);
    List<Mailbox> viewAllInbox(String email);
    List<Mailbox> viewAllOutbox(String email);


}
