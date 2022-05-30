package com.sendMail.sendMail.services;

import com.sendMail.sendMail.datas.models.User;
import com.sendMail.sendMail.datas.repositories.UserRepository;
import com.sendMail.sendMail.dtos.requests.user.*;
import com.sendMail.sendMail.dtos.responses.user.UserResponse;
import com.sendMail.sendMail.exceptions.IncorrectLoginDetailsException;
import com.sendMail.sendMail.exceptions.SendEmailException;
import com.sendMail.sendMail.exceptions.UnMatchingDetailsException;
import com.sendMail.sendMail.exceptions.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    UserRepository userRepository;
    @Override
    public UserResponse register(UserRequest userRequest) {
        Optional<User> optionalUser = userRepository.findById(userRequest.getEmailAddress());
        if(optionalUser.isPresent()) throw new SendEmailException("Email Already Exist");
        User user = new User();
        modelMapper.map(userRequest,user);
        userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setEmailAddress(userRequest.getEmailAddress());
        userResponse.setMessage("Account Successfully Created");

        return userResponse;
    }

    @Override
    public String login(LoginRequest loginRequest) {
        User user= userRepository.findById(loginRequest.getEmailAddress()).orElseThrow(()-> new UserNotFoundException("User does not exist"));
        if(!loginRequest.getPassword().equals(user.getPassword())) {
            throw new IncorrectLoginDetailsException("Incorrect Login details");
        }
        user.setLoggedIn(true);
        userRepository.save(user);
        return "Login Successful";

    }

    @Override
    public UserResponse updateUserDetails(UpdateDetailsRequest updateDetailsRequest,String email) {
        User user = userRepository.findById(email).orElseThrow(() -> new UserNotFoundException("User does not exist"));

        if (user.isLoggedIn()) {
            if (!updateDetailsRequest.getEmailAddress().trim().equals("")) {
                Optional<User> existingUser = userRepository.findById(updateDetailsRequest.getEmailAddress());
                if (existingUser.isPresent()) throw new SendEmailException("Email Already Exist");
                user.setEmailAddress(updateDetailsRequest.getEmailAddress());
            }


            if (!(updateDetailsRequest.getFirstName().trim().equals("") || updateDetailsRequest.getFirstName() == null)) {
                user.setFirstName(updateDetailsRequest.getFirstName());
            }

            if (!(updateDetailsRequest.getLastName().trim().equals("") || updateDetailsRequest.getLastName() == null)) {
                user.setLastName(updateDetailsRequest.getLastName());
            }
        } else {
            throw new IncorrectLoginDetailsException("You must be logged in");
        }
        userRepository.save(user);

        UserResponse updateResponse = new UserResponse();
        modelMapper.map(user, updateResponse);
        updateResponse.setMessage("Details Successfully updated");
        return updateResponse;

    }

    @Override
    public String updatePassword(UpdatePasswordRequest updatePasswordRequest, String email) {
        User user = userRepository.findById(email).orElseThrow(() -> new UserNotFoundException("User does not exist"));
        if (user.isLoggedIn()) {
            if (updatePasswordRequest.getOldPassword().equals(user.getPassword())) {
                if (updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getConfirmNewPassword())) {
                    user.setPassword(updatePasswordRequest.getNewPassword());
                } else {
                    throw new UnMatchingDetailsException("New Password Does Not Match");
                }
            } else {
                throw new UnMatchingDetailsException("Incorrect password");
            }
        } else {
            throw new IncorrectLoginDetailsException("You must be logged in");
        }

        userRepository.save(user);
        return "Password Successfully Updated";
    }

    @Override
    public String deleteAccount(DeleteRequest deleteRequest) {
        User user = userRepository.findById(deleteRequest.getEmailAddress()).orElseThrow(() -> new UserNotFoundException("User does not exist"));
        if(user.isLoggedIn()) {
            if ((deleteRequest.getPassword().equals(deleteRequest.getConfirmPassword()))) {
                if (user.getPassword().equals(deleteRequest.getPassword())) {
                        userRepository.deleteById(deleteRequest.getEmailAddress());
                        return "User Account successfully Deleted";

                    }
                throw new IncorrectLoginDetailsException("Incorrect password!!! Failed To Delete");

            }
            throw new UnMatchingDetailsException("Password Mismatch!!! Failed To Delete");

        }
        return "You must be logged in";
    }


    @Override
    public User findByEmailAddress(String email) {
        return userRepository.findById(email).orElseThrow(() -> new UserNotFoundException("User does not exist"));

    }

    @Override
    public String logout(String email) {
        User user = userRepository.findById(email).orElseThrow(() -> new UserNotFoundException("User does not exist"));
        if (user.isLoggedIn()) {
            user.setLoggedIn(false);
            userRepository.save(user);
            return "Logout Successful";
        }
        return "You are already logged out";
    }
}
