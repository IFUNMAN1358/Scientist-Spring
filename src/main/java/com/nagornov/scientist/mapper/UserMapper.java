package com.nagornov.scientist.mapper;

import com.nagornov.scientist.dto.auth.RegisterFormRequest;
import com.nagornov.scientist.dto.user.UserBiographyResponse;
import com.nagornov.scientist.dto.user.UserRoleResponse;
import com.nagornov.scientist.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(RegisterFormRequest request);

    UserRoleResponse toUserRoleResponse(User user);

    UserBiographyResponse toUserBiographyResponse(User user);

}
