package com.zogg.zoggservice.repository;

import com.zogg.zoggservice.entity.Transaction;
import com.zogg.zoggservice.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}
