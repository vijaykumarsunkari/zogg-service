package com.zogg.zoggservice.converters;

import com.zogg.zoggservice.dtos.VoucherRequestDto;
import com.zogg.zoggservice.dtos.VoucherResponseDto;
import com.zogg.zoggservice.entity.VoucherCollection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VoucherMapper {

    VoucherMapper INSTANCE = Mappers.getMapper(VoucherMapper.class);

    VoucherResponseDto toDto(VoucherCollection voucherCollection);

    List<VoucherResponseDto> toDto(List<VoucherCollection> voucherCollection);

    VoucherCollection toCollection(VoucherRequestDto voucherRequestDto);
}
