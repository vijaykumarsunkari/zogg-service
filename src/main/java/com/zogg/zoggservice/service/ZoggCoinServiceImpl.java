package com.zogg.zoggservice.service;

import com.zogg.zoggservice.dtos.ZoggCoinsRequestDto;
import com.zogg.zoggservice.entity.ZoggCoinTransaction;
import com.zogg.zoggservice.entity.ZoggCoins;
import com.zogg.zoggservice.enums.TransactionType;
import com.zogg.zoggservice.repository.ZoggCoinsRepository;
import com.zogg.zoggservice.service.interfaces.ZoggCoinService;
import com.zogg.zoggservice.utils.CommonUtils;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZoggCoinServiceImpl implements ZoggCoinService {

  private final ZoggCoinsRepository zoggCoinsRepository;
  private final CoinsTransactionalService coinsTransactionalService;

  @Override
  public void updateCoins(ZoggCoinsRequestDto coinsRequestDto) {
    ZoggCoins userCoins = zoggCoinsRepository.findByUserId(coinsRequestDto.getUserId());

    if (TransactionType.CREDIT.equals(coinsRequestDto.getTransactionType())) {

      creditCoins(coinsRequestDto, userCoins);

    } else if (TransactionType.DEBIT.equals(coinsRequestDto.getTransactionType())) {

      debitCoins(coinsRequestDto, userCoins);

    } else {

      throw CommonUtils.logAndGetException("Invalid transaction type");
    }
  }

  @Override
  public void creditCoins(ZoggCoinsRequestDto coinsRequestDto, ZoggCoins userCoins) {

    if (userCoins == null) {

      userCoins = ZoggCoins.builder()
          .userId(coinsRequestDto.getUserId())
          .coins(coinsRequestDto.getNoOfCoins())
          .totalEarned(coinsRequestDto.getNoOfCoins())
          .totalSpent(0L)
          .build();
    } else {

      userCoins.setCoins(userCoins.getCoins() + coinsRequestDto.getNoOfCoins());

      userCoins.setTotalEarned(userCoins.getTotalEarned() + coinsRequestDto.getNoOfCoins());
    }

    ZoggCoinTransaction transaction = saveTransaction(coinsRequestDto, TransactionType.CREDIT);

    coinsTransactionalService.saveCoinsAndTransactions(userCoins,transaction);
  }

  @Override
  public void debitCoins(ZoggCoinsRequestDto coinsRequestDto, ZoggCoins userCoins) {

    if (userCoins == null || userCoins.getCoins() < coinsRequestDto.getNoOfCoins()) {

      throw CommonUtils.logAndGetException("Insufficient balance to redeem coins.");
    }

    userCoins.setCoins(userCoins.getCoins() - coinsRequestDto.getNoOfCoins());

    userCoins.setTotalSpent(userCoins.getTotalSpent() + coinsRequestDto.getNoOfCoins());

    ZoggCoinTransaction transaction = saveTransaction(coinsRequestDto, TransactionType.DEBIT);

    coinsTransactionalService.saveCoinsAndTransactions(userCoins,transaction);
  }

  private ZoggCoinTransaction saveTransaction(ZoggCoinsRequestDto coinsRequestDto, TransactionType transactionType) {

    return ZoggCoinTransaction.builder()
        .userId(coinsRequestDto.getUserId())
        .transactionType(transactionType)
        .refType(coinsRequestDto.getRefType())
        .refValue(coinsRequestDto.getRefValue())
        .coins(coinsRequestDto.getNoOfCoins())
        .createdAt(LocalDateTime.now())
        .build();

  }
}
