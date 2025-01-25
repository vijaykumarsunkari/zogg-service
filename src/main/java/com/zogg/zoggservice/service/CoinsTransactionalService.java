package com.zogg.zoggservice.service;

import com.zogg.zoggservice.entity.Transaction;
import com.zogg.zoggservice.entity.UserWallet;
import com.zogg.zoggservice.repository.TransactionRepository;
import com.zogg.zoggservice.repository.UserWalletRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class CoinsTransactionalService {

    private final UserWalletRepository userWalletRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    void saveWalletAndTransaction(UserWallet userWallet, Transaction transaction) {

        if (Objects.nonNull(userWallet)) {
            userWalletRepository.save(userWallet);
        }
        if (Objects.nonNull(transaction)) {
            transactionRepository.save(transaction);
        }
    }

    @Transactional
    void saveWalletAndTransactionList(UserWallet userWallet, List<Transaction> transactions) {

        if (Objects.nonNull(userWallet)) {
            userWalletRepository.save(userWallet);
        }
        if (!CollectionUtils.isEmpty(transactions)) {
            transactionRepository.saveAll(transactions);
        }
    }
}
