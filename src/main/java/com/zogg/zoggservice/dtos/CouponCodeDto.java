package com.zogg.zoggservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CouponCodeDto {

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("coins_used")
    private Long coinsUsed;

    @JsonProperty("voucher_id")
    private String voucherId;

    @JsonProperty("coupon_code")
    private String couponCode;

    private Boolean redeemed;
}
