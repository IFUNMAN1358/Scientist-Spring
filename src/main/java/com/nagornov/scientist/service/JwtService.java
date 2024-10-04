package com.nagornov.scientist.service;

import com.nagornov.scientist.dto.auth.JwtResponse;
import com.nagornov.scientist.model.User;
import com.nagornov.scientist.repository.JwtRepository;
import com.nagornov.scientist.security.JwtAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtService {

    private JwtRepository jwtRepository;

    public JwtResponse generateTokens(User user) {
        return new JwtResponse(
                jwtRepository.generateAccessToken(user),
                jwtRepository.generateRefreshToken(user)
        );
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
