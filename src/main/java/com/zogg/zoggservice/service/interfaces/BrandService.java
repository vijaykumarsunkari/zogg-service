package com.zogg.zoggservice.service.interfaces;

import com.zogg.zoggservice.dtos.BrandDto;
import java.util.List;

public interface BrandService {

    BrandDto addBrand(BrandDto brandDto);

    BrandDto updateBrand(BrandDto brandDto, Integer userId);

    BrandDto deleteBrand(String brandId);

    List<BrandDto> fetchAllBrand();

    BrandDto fetchBrandById(String brandId);
}
