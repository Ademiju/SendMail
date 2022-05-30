package com.sendMail.sendMail.services;


import com.sendMail.sendMail.datas.models.User;
import com.sendMail.sendMail.dtos.requests.user.*;
import com.sendMail.sendMail.dtos.responses.user.UserResponse;

public interface UserService {
    UserResponse register(UserRequest userRequest);
    String login(LoginRequest loginRequest);
    UserResponse updateUserDetails(UpdateDetailsRequest updateDetailsRequest, String email);
    String updatePassword(UpdatePasswordRequest updatePasswordRequest, String email);
    String deleteAccount(DeleteRequest deleteRequest);
    User findByEmailAddress(String email);
    String logout(String email);




}
