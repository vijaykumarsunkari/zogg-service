package com.zogg.zoggservice.service.interfaces;

import com.zogg.zoggservice.dtos.UserWalletDto;
import com.zogg.zoggservice.enums.CoinTypeEnum;
import com.zogg.zoggservice.enums.TransactionType;

public interface CoinService {

    UserWalletDto getWallet(Integer userId);

    void updateCoins(
            Integer userId, CoinTypeEnum coinType, TransactionType transactionType, Long amount);

    void convertCoins(Integer userId, CoinTypeEnum fromCoin, CoinTypeEnum toCoin, Long amount);

    void updateGameCoins(Integer userId, Long goldCoins, Long gems);
}
