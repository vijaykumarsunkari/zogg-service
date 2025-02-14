package com.zogg.zoggservice.service;

import com.zogg.zoggservice.converters.UserWalletMapper;
import com.zogg.zoggservice.dtos.UserWalletDto;
import com.zogg.zoggservice.entity.Transaction;
import com.zogg.zoggservice.entity.UserWallet;
import com.zogg.zoggservice.enums.CoinTypeEnum;
import com.zogg.zoggservice.enums.TransactionType;
import com.zogg.zoggservice.repository.UserRepository;
import com.zogg.zoggservice.repository.UserWalletRepository;
import com.zogg.zoggservice.service.interfaces.CoinService;
import com.zogg.zoggservice.utils.CommonUtils;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinServiceImpl implements CoinService {

    private final UserWalletRepository walletRepository;
    private final UserRepository userRepository;
    private final CoinsTransactionalService coinsTransactionalService;

    public UserWalletDto getWallet(Integer userId) {

        return UserWalletMapper.INSTANCE.toDto(getWalletInfo(userId));
    }

    public UserWallet getWalletInfo(Integer userId) {

        return walletRepository
                .findByUser(
                        userRepository
                                .findById(userId)
                                .orElseThrow(
                                        () -> CommonUtils.logAndGetException("User not found")))
                .orElseThrow(() -> CommonUtils.logAndGetException("Wallet not found"));
    }

    public void updateCoins(
            Integer userId, CoinTypeEnum coinType, TransactionType transactionType, Long amount) {
        UserWallet wallet = getWalletInfo(userId);

        if (amount <= 0) {
            throw CommonUtils.logAndGetException("Transaction amount must be greater than zero");
        }

        switch (coinType) {
            case ZOGG_COIN:
                wallet.setZoggCoins(
                        calculateNewBalance(
                                wallet.getZoggCoins(), amount, transactionType, "Zogg Coins"));
                break;
            case GOLD_COIN:
                wallet.setGoldCoins(
                        calculateNewBalance(
                                wallet.getGoldCoins(), amount, transactionType, "Gold Coins"));
                break;
            case GEMS:
                wallet.setGems(
                        calculateNewBalance(wallet.getGems(), amount, transactionType, "Gems"));
                break;
            default:
                throw CommonUtils.logAndGetException("Invalid coin type");
        }

        Transaction transaction =
                Transaction.builder()
                        .user(wallet.getUser())
                        .amount(amount)
                        .coinType(coinType)
                        .transactionType(transactionType)
                        .build();

        coinsTransactionalService.saveWalletAndTransaction(wallet, transaction);
    }

    private Long calculateNewBalance(
            Long currentBalance, Long amount, TransactionType transactionType, String coinName) {
        if (transactionType == TransactionType.CREDIT) {
            return currentBalance + amount;
        } else if (transactionType == TransactionType.DEBIT) {
            if (currentBalance < amount) {
                throw CommonUtils.logAndGetException(
                        "Insufficient " + coinName + " balance for debit transaction");
            }
            return currentBalance - amount;
        } else {
            throw CommonUtils.logAndGetException("Invalid transaction type");
        }
    }

    public void convertCoins(
            Integer userId, CoinTypeEnum fromCoin, CoinTypeEnum toCoin, Long amount) {

        UserWallet wallet = getWalletInfo(userId);

        // Define conversion rates
        final int ZOGG_TO_GOLD_CONVERSION_RATE = 10;
        final int ZOGG_TO_GEMS_CONVERSION_RATE = 5;

        if (fromCoin == CoinTypeEnum.ZOGG_COIN && toCoin == CoinTypeEnum.GOLD_COIN) {
            if (wallet.getZoggCoins() < amount) {
                throw CommonUtils.logAndGetException(
                        "Insufficient ZOGG_COIN to convert to GAME_GOLD");
            }
            wallet.setZoggCoins(wallet.getZoggCoins() - amount);
            wallet.setGoldCoins(wallet.getGoldCoins() + amount * ZOGG_TO_GOLD_CONVERSION_RATE);
        } else if (fromCoin == CoinTypeEnum.GOLD_COIN && toCoin == CoinTypeEnum.ZOGG_COIN) {
            if (wallet.getGoldCoins() < amount) {
                throw CommonUtils.logAndGetException(
                        "Insufficient GAME_GOLD to convert to ZOGG_COIN");
            }
            wallet.setGoldCoins(wallet.getGoldCoins() - amount);
            wallet.setZoggCoins(wallet.getZoggCoins() + amount / ZOGG_TO_GOLD_CONVERSION_RATE);

        } else if (fromCoin == CoinTypeEnum.ZOGG_COIN && toCoin == CoinTypeEnum.GEMS) {
            if (wallet.getZoggCoins() < amount) {
                throw CommonUtils.logAndGetException("Insufficient ZOGG_COIN to convert to GEMS");
            }

            wallet.setZoggCoins(wallet.getZoggCoins() - amount);
            wallet.setGems(wallet.getGems() + amount * ZOGG_TO_GEMS_CONVERSION_RATE);

        } else if (fromCoin == CoinTypeEnum.GEMS && toCoin == CoinTypeEnum.ZOGG_COIN) {
            if (wallet.getGems() < amount) {
                throw CommonUtils.logAndGetException("Insufficient GEMS to convert to ZOGG_COIN");
            }
            wallet.setGems(wallet.getGems() - amount);
            wallet.setZoggCoins(wallet.getZoggCoins() + amount / ZOGG_TO_GEMS_CONVERSION_RATE);
        } else {
            throw CommonUtils.logAndGetException("Invalid conversion type");
        }

        Transaction transaction =
                Transaction.builder()
                        .user(wallet.getUser())
                        .transactionType(TransactionType.CONVERSION)
                        .coinType(toCoin)
                        .amount(amount)
                        .build();
        // Log the transaction
        coinsTransactionalService.saveWalletAndTransaction(wallet, transaction);
    }

    @Override
    public void updateGameCoins(Integer userId, Long goldCoins, Long gems) {

        UserWallet wallet = getWalletInfo(userId);

        List<Transaction> transactions = new ArrayList<>();

        if (goldCoins > 0) {
            wallet.setGoldCoins(wallet.getGoldCoins() + goldCoins);
            transactions.add(
                    Transaction.builder()
                            .user(wallet.getUser())
                            .amount(goldCoins)
                            .coinType(CoinTypeEnum.GOLD_COIN)
                            .transactionType(TransactionType.CREDIT)
                            .build());
        }

        if (gems > 0) {
            wallet.setGems(wallet.getGems() + gems);
            transactions.add(
                    Transaction.builder()
                            .user(wallet.getUser())
                            .amount(goldCoins)
                            .coinType(CoinTypeEnum.GEMS)
                            .transactionType(TransactionType.CREDIT)
                            .build());
        }

        coinsTransactionalService.saveWalletAndTransactionList(wallet, transactions);
    }
}
