package com.nagornov.scientist.exception.biography;

import com.nagornov.scientist.exception.HttpException;

public class ScienceNotFoundException extends HttpException {

    public ScienceNotFoundException(String message, Integer code) {
        super(message, code);
    }

}
