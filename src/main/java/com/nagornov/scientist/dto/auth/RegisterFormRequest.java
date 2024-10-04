package com.nagornov.scientist.dto.auth;

import lombok.Data;

@Data
public class RegisterFormRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String patronymic;

}
