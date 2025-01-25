package com.zogg.zoggservice.converters;

import com.zogg.zoggservice.dtos.UserPreferencesDTO;
import com.zogg.zoggservice.entity.UserPreferences;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserPreferencesMapper {
    UserPreferencesMapper INSTANCE = Mappers.getMapper(UserPreferencesMapper.class);

    UserPreferencesDTO toDTO(UserPreferences entity);
}
