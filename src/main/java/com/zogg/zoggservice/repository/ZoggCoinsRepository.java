package com.zogg.zoggservice.repository;

import com.zogg.zoggservice.entity.ZoggCoins;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoggCoinsRepository extends JpaRepository<ZoggCoins, Long> {
    ZoggCoins findByUserId(Integer userId);
}
