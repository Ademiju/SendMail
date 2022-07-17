package com.sendMail.sendMail.exceptions;

public class EmailAlreadyExistsException extends SendEmailException{
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
