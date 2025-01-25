package com.zogg.zoggservice.service.interfaces;

import com.zogg.zoggservice.dtos.CouponCodeDto;
import com.zogg.zoggservice.dtos.CouponCodeRequest;

public interface CouponService {

    Object addCoupons(CouponCodeRequest couponCodeRequest);

    Object redeemCoupon(CouponCodeDto couponCodeDto, Integer userId);
}
