package com.zogg.zoggservice.service;

import com.zogg.zoggservice.converters.VoucherMapper;
import com.zogg.zoggservice.dtos.VoucherRequestDto;
import com.zogg.zoggservice.dtos.VoucherResponseDto;
import com.zogg.zoggservice.entity.BrandCollection;
import com.zogg.zoggservice.entity.CouponCollection;
import com.zogg.zoggservice.entity.VoucherCollection;
import com.zogg.zoggservice.repository.BrandCollectionRepository;
import com.zogg.zoggservice.repository.CouponCollectionRepository;
import com.zogg.zoggservice.repository.VoucherCollectionRepository;
import com.zogg.zoggservice.service.interfaces.VoucherService;
import com.zogg.zoggservice.utils.CommonUtils;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherCollectionRepository voucherCollectionRepository;
    private final BrandCollectionRepository brandCollectionRepository;
    private final CouponCollectionRepository couponCollectionRepository;

    @Override
    public VoucherCollection addVoucher(VoucherRequestDto voucherRequestDto) {

        findBrandCollectionById(voucherRequestDto.getBrandId());

        VoucherCollection voucherCollection =
                VoucherMapper.INSTANCE.toCollection(voucherRequestDto);

        return voucherCollectionRepository.save(voucherCollection);
    }

    private void findBrandCollectionById(String id) {

        brandCollectionRepository
                .findById(id)
                .orElseThrow(() -> CommonUtils.logAndGetException("BrandCollection not found"));
    }

    @Override
    public List<VoucherResponseDto> getVouchers(Integer userId) {

        Map<String, String> voucherIdToReedemedCouponMap =
                couponCollectionRepository.findAllByUserId(userId).stream()
                        .collect(
                                Collectors.toMap(
                                        CouponCollection::getVoucherId,
                                        CouponCollection::getCouponCode));

        List<VoucherCollection> allVoucher = voucherCollectionRepository.findAllByActiveTrue();

        List<VoucherResponseDto> voucherResponseDtos = VoucherMapper.INSTANCE.toDto(allVoucher);

        Set<String> brandIds =
                voucherResponseDtos.stream()
                        .map(VoucherResponseDto::getBrandId)
                        .collect(Collectors.toSet());

        Map<String, BrandCollection> brandIdToBrandMap =
                brandCollectionRepository.findAllById(brandIds).stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toMap(BrandCollection::getId, brand -> brand));

        voucherResponseDtos.forEach(
                voucher -> {
                    if (voucherIdToReedemedCouponMap.containsKey(voucher.getId())) {
                        voucher.setCouponCode(voucherIdToReedemedCouponMap.get(voucher.getId()));
                        voucher.setRedeemed(true);
                    }
                    BrandCollection brand = brandIdToBrandMap.get(voucher.getBrandId());
                    if (brand != null) {
                        voucher.setBrandName(brand.getName());
                        voucher.setBrandDescription(brand.getDescription());
                        voucher.setBrandWebsite(brand.getWebsiteUrl());
                    }
                });

        return voucherResponseDtos;
    }
}
