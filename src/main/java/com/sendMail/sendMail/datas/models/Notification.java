package com.sendMail.sendMail.datas.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Notification {
    private String id;
    private String title;
    private String email;
    private Message message;
}
