package com.zogg.zoggservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameCoinsUpdateRequest {

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("gold_coins")
    private Long goldCoins;

    @JsonProperty("gems")
    private Long gems;
}
