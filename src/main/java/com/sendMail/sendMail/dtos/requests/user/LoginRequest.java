package com.sendMail.sendMail.dtos.requests.user;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequest {
    private String emailAddress;
    private String password;

}
