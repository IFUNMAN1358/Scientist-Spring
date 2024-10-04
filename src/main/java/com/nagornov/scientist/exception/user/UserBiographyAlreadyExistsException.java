package com.nagornov.scientist.exception.user;

import com.nagornov.scientist.exception.HttpException;

public class UserBiographyAlreadyExistsException extends HttpException {

    public UserBiographyAlreadyExistsException(String message, Integer code) {
        super(message, code);
    }

}
