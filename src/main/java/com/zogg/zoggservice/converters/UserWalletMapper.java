package com.zogg.zoggservice.converters;

import com.zogg.zoggservice.dtos.UserWalletDto;
import com.zogg.zoggservice.entity.UserWallet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserWalletMapper {

    UserWalletMapper INSTANCE = Mappers.getMapper(UserWalletMapper.class);

    UserWalletDto toDto(UserWallet userWallet);
}
