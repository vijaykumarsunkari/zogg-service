package com.zogg.zoggservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_wallet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "zogg_coins", nullable = false)
    private Long zoggCoins;

    @Column(name = "gold_coins", nullable = false)
    private Long goldCoins;

    @Column(name = "gems", nullable = false)
    private Long gems;
}
