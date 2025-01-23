package com.zogg.zoggservice.service;

import com.zogg.zoggservice.entity.ZoggCoinTransaction;
import com.zogg.zoggservice.entity.ZoggCoins;
import com.zogg.zoggservice.repository.ZoggCoinTransactionRepository;
import com.zogg.zoggservice.repository.ZoggCoinsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinsTransactionalService {
    private final ZoggCoinsRepository zoggCoinsRepository;
    private final ZoggCoinTransactionRepository zoggCoinTransactionRepository;

    @Transactional
    public void saveCoinsAndTransactions(
            ZoggCoins zoggCoins, ZoggCoinTransaction zoggCoinTransaction) {

        zoggCoinsRepository.save(zoggCoins);
        zoggCoinTransactionRepository.save(zoggCoinTransaction);
    }
}
