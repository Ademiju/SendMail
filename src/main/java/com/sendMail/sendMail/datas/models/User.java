package com.sendMail.sendMail.datas.models;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor

@Document("sendMailUsers")
public class User {

    @Email@Id@NonNull
    private String emailAddress;
    @Min(8)@NonNull
    private String password;
    private String firstName;
    private String lastName;
    private List<Notification> notifications;
    private boolean isLoggedIn;
}
