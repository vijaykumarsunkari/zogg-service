package com.zogg.zoggservice.controller;


import com.zogg.zoggservice.dtos.ApiResponse;
import com.zogg.zoggservice.dtos.BrandDto;
import com.zogg.zoggservice.service.interfaces.BrandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/brand")
@RequiredArgsConstructor
public class BrandController {

  private final BrandService brandService;

  @PostMapping("")
  public ApiResponse<BrandDto> addBrand(@RequestBody BrandDto brandDto) {
    return new ApiResponse<>(brandService.addBrand(brandDto));
  }

  @GetMapping("")
  public ApiResponse<List<BrandDto>> getAllBrands() {
    return new ApiResponse<>(brandService.fetchAllBrand());
  }

}
