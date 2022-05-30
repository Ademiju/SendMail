package com.sendMail.sendMail.exceptions;

public class IncorrectLoginDetailsException extends UnMatchingDetailsException{
    public IncorrectLoginDetailsException(String message) {
        super(message);
    }
}
