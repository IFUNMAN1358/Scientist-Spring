package com.nagornov.scientist.controller;

import com.nagornov.scientist.dto.auth.LoginFormRequest;
import com.nagornov.scientist.dto.auth.AuthResponse;
import com.nagornov.scientist.dto.auth.RegisterFormRequest;
import com.nagornov.scientist.mapper.UserMapper;
import com.nagornov.scientist.model.User;
import com.nagornov.scientist.security.JwtAuthentication;
import com.nagornov.scientist.service.JwtService;
import com.nagornov.scientist.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @PostMapping("/api/auth/registration")
    public ResponseEntity<?> registration(@RequestBody RegisterFormRequest request) {

        User user = userService.createUser(request);

        return ResponseEntity.status(201).body(new AuthResponse(
                userMapper.toUserRoleResponse(user),
                jwtService.generateTokens(user)
        ));
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginFormRequest request) {

        User user = userService.verifyUser(request);

        return ResponseEntity.status(200).body(new AuthResponse(
                userMapper.toUserRoleResponse(user),
                jwtService.generateTokens(user)
        ));
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<?> logout() {
        JwtAuthentication authInfo = jwtService.getAuthInfo();
        return ResponseEntity.status(200).body("Successful logout");
    }

}
