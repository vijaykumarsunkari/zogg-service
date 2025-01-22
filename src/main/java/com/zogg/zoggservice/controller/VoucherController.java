package com.zogg.zoggservice.controller;

import com.zogg.zoggservice.dtos.ApiResponse;
import com.zogg.zoggservice.dtos.VoucherDto;
import com.zogg.zoggservice.service.interfaces.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/{user_id}/voucher")
@RequiredArgsConstructor
public class VoucherController {

  private final VoucherService voucherService;

  @PostMapping("")
  public ApiResponse<?> addVoucher(@RequestBody VoucherDto voucherDto) {

    return new ApiResponse<>(voucherService.addVoucher(voucherDto));
  }

  @GetMapping("")
  public ApiResponse<?> getVouchers(@PathVariable("user_id") String userId) {
    return new ApiResponse<>(voucherService.getVouchers(userId));
  }
}
