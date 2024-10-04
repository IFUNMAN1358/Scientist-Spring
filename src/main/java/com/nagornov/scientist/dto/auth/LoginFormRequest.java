package com.nagornov.scientist.dto.auth;

import lombok.Data;

@Data
public class LoginFormRequest {

    private String email;
    private String password;

}
