package com.sendMail.sendMail.datas.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Notification {
    @Id
    private String id;
    private LocalTime time = LocalTime.now();
    private String title;
    private String messageBody;
}
