package com.nagornov.scientist.mapper;

import com.nagornov.scientist.dto.user.BiographyFormRequest;
import com.nagornov.scientist.model.Biography;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BiographyMapper {

    @Mapping(target = "biographyId", ignore = true)
    @Mapping(target = "imageName", ignore = true)
    @Mapping(target = "science", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isConfirmed", ignore = true)
    Biography toBiography(BiographyFormRequest request);

}
