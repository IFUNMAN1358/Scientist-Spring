package com.nagornov.scientist.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HttpException extends RuntimeException {

    private String message;
    private Integer code;

}
