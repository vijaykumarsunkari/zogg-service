package com.zogg.zoggservice.service;

import com.zogg.zoggservice.dtos.CouponCodeDto;
import com.zogg.zoggservice.dtos.CouponCodeRequest;
import com.zogg.zoggservice.dtos.ZoggCoinsRequestDto;
import com.zogg.zoggservice.entity.CouponCollection;
import com.zogg.zoggservice.entity.ZoggCoins;
import com.zogg.zoggservice.repository.CouponCollectionRepository;
import com.zogg.zoggservice.repository.ZoggCoinsRepository;
import com.zogg.zoggservice.service.interfaces.CouponService;
import com.zogg.zoggservice.service.interfaces.ZoggCoinService;
import com.zogg.zoggservice.utils.CommonUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

  private final CouponCollectionRepository couponCollectionRepository;
  private final MongoTemplate mongoTemplate;
  private final ZoggCoinsRepository zoggCoinsRepository;
  private final ZoggCoinService zoggCoinService;

  @Override
  public Object addCoupons(CouponCodeRequest couponCodeRequest) {
    List<CouponCollection> couponCollections = couponCodeRequest.getCouponCodes().stream().map(
        couponCode -> CouponCollection.builder().couponCode(couponCode)
            .createdAt(LocalDateTime.now()).voucherId(couponCodeRequest.getVoucherId())
            .redeemed(false).build()).collect(Collectors.toList());

    return couponCollectionRepository.saveAll(couponCollections);
  }

  @Override
  public Object redeemCoupon(CouponCodeDto couponCodeDto, String userId) {

    ZoggCoins userCoins = zoggCoinsRepository.findByUserId(couponCodeDto.getUserId());

    ZoggCoinsRequestDto zoggCoinsRequestDto = ZoggCoinsRequestDto.builder()
        .noOfCoins(couponCodeDto.getCoinsUsed()).refType("COUPON_REDEEM")
        .userId(couponCodeDto.getUserId()).build();

    zoggCoinService.debitCoins(zoggCoinsRequestDto, userCoins);

    Query query = new Query(
        Criteria.where("voucherId").is(couponCodeDto.getVoucherId()).and("redeemed").is(false));

    Update update = new Update().set("redeemed", true).set("userId", userId)
        .currentDate("redeemedAt");

    CouponCollection redeemedCoupon = mongoTemplate.findAndModify(query, update,
        FindAndModifyOptions.options().returnNew(true), CouponCollection.class);

    if (redeemedCoupon == null) {

      throw CommonUtils.logAndGetException("No available coupons to redeem for this voucher.");
    }

    return CouponCodeDto.builder().coinsUsed(couponCodeDto.getCoinsUsed())
        .userId(couponCodeDto.getUserId()).couponCode(redeemedCoupon.getCouponCode())
        .voucherId(redeemedCoupon.getVoucherId()).redeemed(redeemedCoupon.getRedeemed()).build();
  }
}
