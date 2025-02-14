package com.zogg.zoggservice.controller;

import com.zogg.zoggservice.dtos.ApiResponse;
import com.zogg.zoggservice.dtos.VoucherRequestDto;
import com.zogg.zoggservice.dtos.VoucherResponseDto;
import com.zogg.zoggservice.entity.VoucherCollection;
import com.zogg.zoggservice.service.interfaces.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{user_id}/voucher")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    @PostMapping("")
    public ApiResponse<VoucherCollection> addVoucher(
            @RequestBody VoucherRequestDto voucherRequestDto) {

        return new ApiResponse<>(voucherService.addVoucher(voucherRequestDto));
    }

    @PutMapping("")
    public ApiResponse<VoucherResponseDto> updateVoucher(
            @PathVariable("user_id") Integer userId,
            @RequestBody VoucherRequestDto voucherRequestDto) {
        return new ApiResponse<>(voucherService.updateVoucher(voucherRequestDto, userId));
    }

    @DeleteMapping("/{voucher_id}")
    public ApiResponse<Void> deleteVoucher(@PathVariable("voucher_id") String voucherId) {
        voucherService.deleteVoucher(voucherId);
        return new ApiResponse<>();
    }

    @GetMapping("")
    public ApiResponse<?> getVouchers(@PathVariable("user_id") Integer userId) {
        return new ApiResponse<>(voucherService.getVouchers(userId));
    }

    @GetMapping("/{voucher_id}")
    public ApiResponse<?> getVouchers(
            @PathVariable("user_id") Integer userId, @PathVariable("voucher_id") String voucherId) {
        return new ApiResponse<>(voucherService.getVoucherById(voucherId));
    }
}
