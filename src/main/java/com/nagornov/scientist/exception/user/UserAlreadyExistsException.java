package com.nagornov.scientist.exception.user;

import com.nagornov.scientist.exception.HttpException;


public class UserAlreadyExistsException extends HttpException {

    public UserAlreadyExistsException(String message, Integer code) {
        super(message, code);
    }
}
