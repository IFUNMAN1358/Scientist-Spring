package com.nagornov.scientist.exception.biography;

import com.nagornov.scientist.exception.HttpException;

public class UserBiographyNotFoundException extends HttpException {

    public UserBiographyNotFoundException(String message, Integer code) {
        super(message, code);
    }

}
