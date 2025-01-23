package com.zogg.zoggservice.controller;

import com.zogg.zoggservice.dtos.ApiResponse;
import com.zogg.zoggservice.dtos.CouponCodeDto;
import com.zogg.zoggservice.dtos.CouponCodeRequest;
import com.zogg.zoggservice.service.interfaces.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/{user_id}/coupon")
@RequiredArgsConstructor
public class couponController {

    private final CouponService couponService;

    @PostMapping()
    public ApiResponse<?> addCoupons(@RequestBody CouponCodeRequest couponCodeRequest) {

        return new ApiResponse<>(couponService.addCoupons(couponCodeRequest));
    }

    @PostMapping("/redeem")
    public ApiResponse<?> redeemCoupons(
            @PathVariable("user_id") String userId, @RequestBody CouponCodeDto couponCodeDto) {

        return new ApiResponse<>(couponService.redeemCoupon(couponCodeDto, userId));
    }
}
