package com.nagornov.scientist.dto.user;

import com.nagornov.scientist.model.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class UserRoleResponse {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String patronymic;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Set<Role> roles;

}
