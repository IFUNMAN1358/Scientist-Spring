package com.nagornov.scientist.controller;

import com.nagornov.scientist.dto.user.ChangePasswordRequest;
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
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/api/user/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        JwtAuthentication authInfo = jwtService.getAuthInfo();
        userService.changePassword(authInfo, request);
        return ResponseEntity.status(204).body("Password changed");
    }

}
