package com.sendMail.sendMail.exceptions;

public class UserNotFoundException extends SendEmailException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
