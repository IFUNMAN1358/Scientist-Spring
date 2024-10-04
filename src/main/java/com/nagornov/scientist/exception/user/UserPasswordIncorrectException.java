package com.nagornov.scientist.exception.user;

import com.nagornov.scientist.exception.HttpException;


public class UserPasswordIncorrectException extends HttpException {

    public UserPasswordIncorrectException(String message, Integer code) {
        super(message, code);
    }
}
