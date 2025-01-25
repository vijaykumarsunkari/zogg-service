package com.zogg.zoggservice.service.interfaces;

import com.zogg.zoggservice.entity.UserWallet;
import com.zogg.zoggservice.enums.CoinTypeEnum;

public interface CoinService {

    UserWallet getWallet(Integer userId);

    void updateCoins(Integer userId, CoinTypeEnum coinType, Long amount);

    void convertCoins(Integer userId, CoinTypeEnum fromCoin, CoinTypeEnum toCoin, Long amount);

    void updateGameCoins(Integer userId, Long goldCoins, Long gems);
}
