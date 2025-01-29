package com.zogg.zoggservice.controller;

import com.zogg.zoggservice.dtos.ApiResponse;
import com.zogg.zoggservice.dtos.BrandDto;
import com.zogg.zoggservice.service.interfaces.BrandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping("")
    public ApiResponse<BrandDto> addBrand(@RequestBody BrandDto brandDto) {
        return new ApiResponse<>(brandService.addBrand(brandDto));
    }

    @PutMapping()
    public ApiResponse<BrandDto> updateBrand(@RequestBody BrandDto brandDto) {

        return new ApiResponse<>(brandService.updateBrand(brandDto));
    }

    @DeleteMapping("/{brand_id}")
    public ApiResponse<?> deleteBrand(@PathVariable("brand_id") String brandId) {

        brandService.deleteBrand(brandId);
        return new ApiResponse<>("Brand deleted successfully");
    }

    @GetMapping("")
    public ApiResponse<List<BrandDto>> getAllBrands() {
        return new ApiResponse<>(brandService.fetchAllBrand());
    }
}
