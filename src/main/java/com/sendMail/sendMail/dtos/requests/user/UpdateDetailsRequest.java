package com.sendMail.sendMail.dtos.requests.user;

import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDetailsRequest {
    private String emailAddress;
    private String firstName;
    private String lastName;


}
