package com.zogg.zoggservice.converters;

import com.zogg.zoggservice.dtos.FcmTokenUpdateDto;
import com.zogg.zoggservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FcmTokenMapper {

    FcmTokenMapper INSTANCE = Mappers.getMapper(FcmTokenMapper.class);

    @Mapping(target = "fcmToken", source = "fcmToken")
    void updateFcmTokenFromDto(FcmTokenUpdateDto dto, @MappingTarget User user);

    @Mapping(target = "fcmToken", source = "fcmToken")
    FcmTokenUpdateDto toDto(User user);
}
