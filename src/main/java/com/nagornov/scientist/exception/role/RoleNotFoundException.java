package com.nagornov.scientist.exception.role;

import com.nagornov.scientist.exception.HttpException;


public class RoleNotFoundException extends HttpException {

    public RoleNotFoundException(String message, Integer code) {
        super(message, code);
    }

}
