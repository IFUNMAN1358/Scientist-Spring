package com.nagornov.scientist.exception.biography;

import com.nagornov.scientist.exception.HttpException;

public class LocationNotFoundException extends HttpException {

    public LocationNotFoundException(String message, Integer code) {
        super(message, code);
    }

}
