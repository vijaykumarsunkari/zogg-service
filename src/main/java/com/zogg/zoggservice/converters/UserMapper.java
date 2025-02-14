package com.zogg.zoggservice.converters;

import com.zogg.zoggservice.dtos.UserDto;
import com.zogg.zoggservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "updatedBy", target = "updatedBy")
    UserDto toDto(User user);

    @Mapping(target = "active", ignore = true)
    @Mapping(target = "blacklisted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "updatedBy", target = "updatedBy")
    User toEntity(UserDto userDto);
}
