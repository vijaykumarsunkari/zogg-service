package com.zogg.zoggservice.repository;

import com.zogg.zoggservice.entity.ZoggCoinTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoggCoinTransactionRepository extends JpaRepository<ZoggCoinTransaction, Long> {}
