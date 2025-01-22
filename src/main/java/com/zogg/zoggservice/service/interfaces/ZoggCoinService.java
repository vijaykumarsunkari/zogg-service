package com.zogg.zoggservice.service.interfaces;

import com.zogg.zoggservice.dtos.ZoggCoinsRequestDto;
import com.zogg.zoggservice.entity.ZoggCoins;

public interface ZoggCoinService {

  void updateCoins(ZoggCoinsRequestDto coinsRequestDto);

  void creditCoins(ZoggCoinsRequestDto coinsRequestDto, ZoggCoins userCoins);

  void debitCoins(ZoggCoinsRequestDto coinsRequestDto, ZoggCoins userCoins);
}
