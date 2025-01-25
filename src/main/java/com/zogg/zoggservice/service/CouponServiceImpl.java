package com.zogg.zoggservice.service;

import com.zogg.zoggservice.dtos.CouponCodeDto;
import com.zogg.zoggservice.dtos.CouponCodeRequest;
import com.zogg.zoggservice.entity.CouponCollection;
import com.zogg.zoggservice.entity.Transaction;
import com.zogg.zoggservice.entity.UserWallet;
import com.zogg.zoggservice.enums.CoinTypeEnum;
import com.zogg.zoggservice.enums.TransactionType;
import com.zogg.zoggservice.repository.CouponCollectionRepository;
import com.zogg.zoggservice.repository.UserRepository;
import com.zogg.zoggservice.repository.UserWalletRepository;
import com.zogg.zoggservice.service.interfaces.CouponService;
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
    private final UserWalletRepository walletRepository;
    private final UserRepository userRepository;
    private CoinsTransactionalService coinsTransactionalService;

    @Override
    public Object addCoupons(CouponCodeRequest couponCodeRequest) {
        List<CouponCollection> couponCollections =
                couponCodeRequest.getCouponCodes().stream()
                        .map(
                                couponCode ->
                                        CouponCollection.builder()
                                                .couponCode(couponCode)
                                                .createdAt(LocalDateTime.now())
                                                .voucherId(couponCodeRequest.getVoucherId())
                                                .redeemed(false)
                                                .build())
                        .collect(Collectors.toList());

        return couponCollectionRepository.saveAll(couponCollections);
    }

    @Override
    public Object redeemCoupon(CouponCodeDto couponCodeDto, Integer userId) {

        // Retrieve user wallet with app coins only
        UserWallet userWallet =
                walletRepository
                        .findByUser(
                                userRepository
                                        .findById(userId)
                                        .orElseThrow(() -> new RuntimeException("User not found")))
                        .orElseThrow(() -> new RuntimeException("Wallet not found"));

        // Check if the user has enough app coins to redeem the coupon
        if (userWallet.getZoggCoins() < couponCodeDto.getCoinsUsed()) {
            throw new RuntimeException("Insufficient app coins to redeem the coupon.");
        }

        // Deduct coins from wallet
        userWallet.setZoggCoins(userWallet.getZoggCoins() - couponCodeDto.getCoinsUsed());

        // Create a transaction record
        Transaction transaction =
                Transaction.builder()
                        .user(userWallet.getUser())
                        .coinType(CoinTypeEnum.ZOGG_COIN)
                        .transactionType(TransactionType.DEBIT)
                        .amount(couponCodeDto.getCoinsUsed())
                        .build();

        coinsTransactionalService.saveWalletAndTransaction(userWallet, transaction);

        // Redeem the coupon by updating MongoDB collection
        Query query =
                new Query(
                        Criteria.where("voucherId")
                                .is(couponCodeDto.getVoucherId())
                                .and("redeemed")
                                .is(false));

        Update update =
                new Update().set("redeemed", true).set("userId", userId).currentDate("redeemedAt");

        CouponCollection redeemedCoupon =
                mongoTemplate.findAndModify(
                        query,
                        update,
                        FindAndModifyOptions.options().returnNew(true),
                        CouponCollection.class);

        if (redeemedCoupon == null) {
            throw new RuntimeException("No available coupons to redeem for this voucher.");
        }

        // Return successful coupon redemption response
        return CouponCodeDto.builder()
                .coinsUsed(couponCodeDto.getCoinsUsed())
                .userId(userId)
                .couponCode(redeemedCoupon.getCouponCode())
                .voucherId(redeemedCoupon.getVoucherId())
                .redeemed(redeemedCoupon.getRedeemed())
                .build();
    }
}
