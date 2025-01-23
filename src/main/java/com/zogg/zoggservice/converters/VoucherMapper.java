package com.zogg.zoggservice.converters;

import com.zogg.zoggservice.dtos.VoucherDto;
import com.zogg.zoggservice.entity.VoucherCollection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VoucherMapper {

    VoucherMapper INSTANCE = Mappers.getMapper(VoucherMapper.class);

    VoucherDto toDto(VoucherCollection voucherCollection);

    List<VoucherDto> toDto(List<VoucherCollection> voucherCollection);

    VoucherCollection toCollection(VoucherDto voucherDto);
}
