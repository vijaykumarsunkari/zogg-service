package com.zogg.zoggservice.service;

import com.zogg.zoggservice.converters.VoucherMapper;
import com.zogg.zoggservice.dtos.VoucherDto;
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
    public Object addVoucher(VoucherDto voucherDto) {
        BrandCollection brandCollection =
                brandCollectionRepository.findById(voucherDto.getBrandId()).orElse(null);

        if (brandCollection == null) {
            throw CommonUtils.logAndGetException("BrandCollection not found");
        }

        VoucherCollection voucherCollection = VoucherMapper.INSTANCE.toCollection(voucherDto);
        return voucherCollectionRepository.save(voucherCollection);
    }

    @Override
    public List<VoucherDto> getVouchers(String userId) {

        List<String> redeemedVoucherIds =
                couponCollectionRepository.findAllByUserId(Integer.parseInt(userId)).stream()
                        .map(CouponCollection::getVoucherId)
                        .toList();

        List<VoucherCollection> availableVouchers =
                voucherCollectionRepository.findAllByIdNotIn(redeemedVoucherIds);

        List<VoucherDto> voucherDtos = VoucherMapper.INSTANCE.toDto(availableVouchers);

        Set<String> brandIds =
                voucherDtos.stream().map(VoucherDto::getBrandId).collect(Collectors.toSet());

        Map<String, BrandCollection> brandIdToBrandMap =
                brandCollectionRepository.findAllById(brandIds).stream()
                        .collect(Collectors.toMap(BrandCollection::getId, brand -> brand));

        voucherDtos.forEach(
                voucher -> {
                    BrandCollection brand = brandIdToBrandMap.get(voucher.getBrandId());
                    if (brand != null) {
                        voucher.setBrandName(brand.getName());
                        voucher.setBrandDescription(brand.getDescription());
                        voucher.setBrandWebsite(brand.getWebsiteUrl());
                    }
                });

        return voucherDtos;
    }
}
