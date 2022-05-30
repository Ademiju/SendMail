package com.sendMail.sendMail.dtos.requests.user;

import lombok.*;

@Getter
@Setter@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

}
