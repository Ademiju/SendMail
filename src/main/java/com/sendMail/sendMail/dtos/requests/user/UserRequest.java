package com.sendMail.sendMail.dtos.requests.user;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @Email
    @Id
    @NonNull
    private String emailAddress;
    @Min(8)@NonNull
    private String password;
    private String firstName;
    private String lastName;

}
