package com.zogg.zoggservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zogg.zoggservice.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZoggCoinsRequestDto {

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("no_of_coins")
    private Long noOfCoins;

    @JsonProperty("transaction_type")
    private TransactionType transactionType;

    @JsonProperty("ref_type")
    private String refType;

    @JsonProperty("ref_value")
    private String refValue;
}
