package com.zogg.zoggservice.converters;

import com.zogg.zoggservice.dtos.BrandDto;
import com.zogg.zoggservice.entity.BrandCollection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BrandCollectionMapper {

    BrandCollectionMapper INSTANCE = Mappers.getMapper(BrandCollectionMapper.class);

    BrandDto toDto(BrandCollection brandCollection);

    List<BrandDto> toDto(List<BrandCollection> brandCollections);

    BrandCollection toCollection(BrandDto brandDto);
}
