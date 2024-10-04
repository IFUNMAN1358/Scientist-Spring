package com.nagornov.scientist.service;

import com.nagornov.scientist.dto.auth.LoginFormRequest;
import com.nagornov.scientist.dto.auth.RegisterFormRequest;
import com.nagornov.scientist.dto.user.ChangePasswordRequest;
import com.nagornov.scientist.exception.role.RoleNotFoundException;
import com.nagornov.scientist.exception.user.UserAlreadyExistsException;
import com.nagornov.scientist.exception.user.UserNotFoundException;
import com.nagornov.scientist.exception.user.UserPasswordIncorrectException;
import com.nagornov.scientist.mapper.UserMapper;
import com.nagornov.scientist.model.Role;
import com.nagornov.scientist.model.User;
import com.nagornov.scientist.repository.RoleRepository;
import com.nagornov.scientist.repository.UserRepository;
import com.nagornov.scientist.security.JwtAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public User createUser(RegisterFormRequest request) {

        Optional<User> existingUser = userRepository.findUserByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists", 500);
        }

        Optional<Role> existingRole = roleRepository.findRoleByName("ROLE_USER");
        if (existingRole.isEmpty()) {
            throw new RoleNotFoundException("Role not found", 500);
        }

        User userFromForm = userMapper.toUser(request);
        userFromForm.addRole(existingRole.get());
        userFromForm.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(userFromForm);
    }

    public User verifyUser(LoginFormRequest request) {

        Optional<User> existingUser = userRepository.findUserByEmail(request.getEmail());
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("User not found", 500);
        }

        if (!passwordEncoder.matches(request.getPassword(), existingUser.get().getPassword())) {
            throw new UserPasswordIncorrectException("Password is incorrect", 400);
        }

        return existingUser.get();
    }

    public void changePassword(JwtAuthentication authInfo, ChangePasswordRequest request) {
        Optional<User> existingUser = userRepository.findUserByUserId(UUID.fromString(authInfo.getUserId()));
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("User not found", 500);
        }
        User user = existingUser.get();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new UserPasswordIncorrectException("Password is incorrect", 400);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

}
