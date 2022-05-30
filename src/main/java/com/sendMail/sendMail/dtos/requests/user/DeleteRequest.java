package com.sendMail.sendMail.dtos.requests.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteRequest {
     private String emailAddress;
     private String password;
     private String confirmPassword;
}
