package com.zogg.zoggservice.service.interfaces;

import com.zogg.zoggservice.dtos.VoucherRequestDto;
import com.zogg.zoggservice.dtos.VoucherResponseDto;
import com.zogg.zoggservice.entity.VoucherCollection;
import java.util.List;

public interface VoucherService {

    VoucherCollection addVoucher(VoucherRequestDto voucherRequestDto);

    VoucherResponseDto updateVoucher(String voucherId, VoucherRequestDto voucherRequestDto);

    void deleteVoucher(String voucherId);

    List<VoucherResponseDto> getVouchers(Integer userId);
}
