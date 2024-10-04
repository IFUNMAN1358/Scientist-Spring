package com.nagornov.scientist.dto.auth;

import com.nagornov.scientist.dto.user.UserRoleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private UserRoleResponse user;
    private JwtResponse jwt;

}
