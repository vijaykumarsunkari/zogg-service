package com.zogg.zoggservice.repository;

import com.zogg.zoggservice.entity.User;
import com.zogg.zoggservice.entity.UserWallet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {
    Optional<UserWallet> findByUser(User user);
}
