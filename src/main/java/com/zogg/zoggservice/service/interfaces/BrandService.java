package com.zogg.zoggservice.service.interfaces;

import com.zogg.zoggservice.dtos.BrandDto;
import java.util.List;

public interface BrandService {

    BrandDto addBrand(BrandDto brandDto);

    List<BrandDto> fetchAllBrand();
}
