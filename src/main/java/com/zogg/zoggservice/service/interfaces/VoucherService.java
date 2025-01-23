package com.zogg.zoggservice.service.interfaces;

import com.zogg.zoggservice.dtos.VoucherDto;
import java.util.List;

public interface VoucherService {

    Object addVoucher(VoucherDto voucherDto);

    List<VoucherDto> getVouchers(String userId);
}
