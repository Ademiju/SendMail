package com.sendMail.sendMail.datas.models;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Email@Id
    private String emailAddress;
    @Min(8)
    private String password;
    private String firstName;
    private String lastName;
    List<Notification> notifications;
}
