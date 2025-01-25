package com.zogg.zoggservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zogg.zoggservice.enums.CoinTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoinRequestDto {

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("coin_type")
    private CoinTypeEnum coinType;

    private Long amount;
}
