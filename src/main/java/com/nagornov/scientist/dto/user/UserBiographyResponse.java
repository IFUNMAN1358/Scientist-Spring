package com.nagornov.scientist.dto.user;

import com.nagornov.scientist.model.Biography;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserBiographyResponse {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String patronymic;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Biography biography;

}
