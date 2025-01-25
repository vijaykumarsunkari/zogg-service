package com.zogg.zoggservice.dtos;

import com.zogg.zoggservice.enums.CoinTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConvertRequestDto {
    private Integer userId;
    private CoinTypeEnum fromCoin;
    private CoinTypeEnum toCoin;
    private Long amount;
}
