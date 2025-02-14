package com.zogg.zoggservice.service;

import com.zogg.zoggservice.converters.BrandCollectionMapper;
import com.zogg.zoggservice.dtos.BrandDto;
import com.zogg.zoggservice.entity.BrandCollection;
import com.zogg.zoggservice.repository.BrandCollectionRepository;
import com.zogg.zoggservice.repository.UserRepository;
import com.zogg.zoggservice.service.interfaces.BrandService;
import com.zogg.zoggservice.utils.CommonUtils;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandCollectionRepository brandCollectionRepository;
    private final UserRepository userRepository;

    @Override
    public BrandDto addBrand(BrandDto brandDto) {
        if (brandCollectionRepository.findByNameAndActiveTrue(brandDto.getName()) != null) {
            throw CommonUtils.logAndGetException("Brand name already exists" + brandDto.getName());
        }
        BrandCollection brandCollection = BrandCollectionMapper.INSTANCE.toCollection(brandDto);
        return BrandCollectionMapper.INSTANCE.toDto(
                brandCollectionRepository.save(brandCollection));
    }

    @Override
    public BrandDto updateBrand(BrandDto brandDto, Integer userId) {

        if (brandDto.getId() == null) {
            throw CommonUtils.logAndGetException("Brand ID is required for update");
        }

        BrandCollection existingBrand =
                brandCollectionRepository
                        .findById(brandDto.getId())
                        .orElseThrow(
                                () ->
                                        CommonUtils.logAndGetException(
                                                "Brand not found with ID: " + brandDto.getId()));

        if (brandDto.getName() != null
                && !brandDto.getName().equals(existingBrand.getName())
                && brandCollectionRepository.findByNameAndActiveTrue(brandDto.getName()) != null) {
            throw CommonUtils.logAndGetException(
                    "Brand name already exists: " + brandDto.getName());
        }

        existingBrand.setName(
                Objects.nonNull(brandDto.getName()) ? brandDto.getName() : existingBrand.getName());

        existingBrand.setDescription(
                Objects.nonNull(brandDto.getDescription())
                        ? brandDto.getDescription()
                        : existingBrand.getDescription());

        existingBrand.setMediaDetails(
                CollectionUtils.isEmpty(brandDto.getMediaDetails())
                        ? existingBrand.getMediaDetails()
                        : CommonUtils.updateMediaDetails(
                                existingBrand.getMediaDetails(), brandDto.getMediaDetails()));

        existingBrand.setWebsiteUrl(
                Objects.nonNull(brandDto.getWebsiteUrl())
                        ? brandDto.getWebsiteUrl()
                        : existingBrand.getWebsiteUrl());

        existingBrand.setBusinessCategory(
                Objects.nonNull(brandDto.getBusinessCategory())
                        ? brandDto.getBusinessCategory()
                        : existingBrand.getBusinessCategory());
        existingBrand.setUpdatedBy(userId);

        BrandCollection savedBrand = brandCollectionRepository.save(existingBrand);

        return BrandCollectionMapper.INSTANCE.toDto(savedBrand);
    }

    @Override
    @Transactional
    public BrandDto deleteBrand(String brandId) {

        if (brandId == null) {
            throw CommonUtils.logAndGetException("Brand ID is required for deletion");
        }

        BrandCollection existingBrand =
                brandCollectionRepository
                        .findById(brandId)
                        .orElseThrow(
                                () ->
                                        CommonUtils.logAndGetException(
                                                "Brand not found with ID: " + brandId));

        existingBrand.setActive(false);
        BrandCollection savedBrand = brandCollectionRepository.save(existingBrand);

        return BrandCollectionMapper.INSTANCE.toDto(savedBrand);
    }

    @Override
    public List<BrandDto> fetchAllBrand() {
        List<BrandCollection> brandCollections = brandCollectionRepository.findAll();
        return BrandCollectionMapper.INSTANCE.toDto(brandCollections);
    }

    @Override
    public BrandDto fetchBrandById(String brandId) {

        BrandCollection brandCollection =
                brandCollectionRepository
                        .findById(brandId)
                        .orElseThrow(
                                () ->
                                        CommonUtils.logAndGetException(
                                                "Brand not found with ID: " + brandId));
        return BrandCollectionMapper.INSTANCE.toDto(brandCollection);
    }
}
