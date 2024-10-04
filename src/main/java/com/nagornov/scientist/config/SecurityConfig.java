package com.nagornov.scientist.config;

import com.nagornov.scientist.security.exception.CustomAccessDeniedHandler;
import com.nagornov.scientist.security.exception.CustomAuthenticationEntryPoint;
import com.nagornov.scientist.security.filter.CustomCorsFilter;
import com.nagornov.scientist.security.filter.CustomCsrfFilter;
import com.nagornov.scientist.security.filter.CustomJwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomCorsFilter corsFilter;
    private final CustomCsrfFilter csrfFilter;
    private final CustomJwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
          http
            .httpBasic(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
            .addFilterBefore(csrfFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth

                    // AUTH
                    .requestMatchers(HttpMethod.POST, "/api/auth/registration").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auth/logout").hasRole("USER")

                    // USER
                    .requestMatchers(HttpMethod.POST, "/api/user/password").hasRole("USER")

                    // BIOGRAPHY
                    // all
                    .requestMatchers(HttpMethod.GET, "/api/biography/image/{imageName}").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/biography/all/science").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/biography/all/location").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/biography/all/science/{scienceId}").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/biography/all/location/{locationId}").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/biography/all/search/{fio}").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/biography/{userId}").permitAll()
                    // user
                    .requestMatchers(HttpMethod.GET, "/api/biography").hasRole("USER")
                    .requestMatchers(HttpMethod.POST, "/api/biography").hasRole("USER")
                    .requestMatchers(HttpMethod.PUT, "/api/biography").hasRole("USER")
                    .requestMatchers(HttpMethod.DELETE, "/api/biography").hasRole("USER")
                    // manager
                    .requestMatchers(HttpMethod.GET, "/api/biography/all/unconfirmed").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.PATCH, "/api/biography/{userId}/confirm").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.DELETE, "/api/biography/{userId}").hasRole("MANAGER")

                    // SCIENCE
                    .requestMatchers(HttpMethod.GET, "/api/science/all").permitAll()
                    
                    // LOCATION
                    .requestMatchers(HttpMethod.GET, "/api/location/all").permitAll()

                    .anyRequest().authenticated()
            )
            .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
            );
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/resources/**");
    }
}
