package com.sendMail.sendMail.dtos.responses.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String emailAddress;
    private String firstName;
    private String lastName;
    private String message;
}
