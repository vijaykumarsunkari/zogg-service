package com.zogg.zoggservice.service;

import com.zogg.zoggservice.converters.BrandCollectionMapper;
import com.zogg.zoggservice.dtos.BrandDto;
import com.zogg.zoggservice.entity.BrandCollection;
import com.zogg.zoggservice.repository.BrandCollectionRepository;
import com.zogg.zoggservice.service.interfaces.BrandService;
import com.zogg.zoggservice.utils.CommonUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

  private final BrandCollectionRepository brandCollectionRepository;

  @Override
  public BrandDto addBrand(BrandDto brandDto) {
    if (brandCollectionRepository.findByName(brandDto.getName()) != null) {
      throw CommonUtils.logAndGetException("Brand name already exists" + brandDto.getName());
    }
    BrandCollection brandCollection = BrandCollectionMapper.INSTANCE.toCollection(brandDto);
    return BrandCollectionMapper.INSTANCE.toDto(brandCollectionRepository.save(brandCollection));
  }

  @Override
  public List<BrandDto> fetchAllBrand() {
    List<BrandCollection> brandCollections = brandCollectionRepository.findAll();
    return BrandCollectionMapper.INSTANCE.toDto(brandCollections);
  }
}
