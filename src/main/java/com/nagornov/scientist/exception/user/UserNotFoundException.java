package com.nagornov.scientist.exception.user;

import com.nagornov.scientist.exception.HttpException;


public class UserNotFoundException extends HttpException {

    public UserNotFoundException(String message, Integer code) {
        super(message, code);
    }
}
