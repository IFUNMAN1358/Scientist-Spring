package com.nagornov.scientist.security.filter;

import com.nagornov.scientist.repository.JwtRepository;
import com.nagornov.scientist.security.JwtAuthentication;
import com.nagornov.scientist.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomJwtFilter extends GenericFilterBean {

    private final JwtRepository jwtRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain fc) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = JwtUtil.getTokenFromRequest(request);

        if (token != null && jwtRepository.validateAccessToken(token)) {
            final Claims claims = jwtRepository.getAccessClaims(token);
            final JwtAuthentication jwtInfoToken = JwtUtil.generateAccessInfo(claims);
            jwtInfoToken.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
        }
        fc.doFilter(request, response);
    }

}
