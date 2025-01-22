package com.zogg.zoggservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponCodeRequest {


  @NotNull(message = "Voucher id is null")
  @JsonProperty("voucher_id")
  private String voucherId;

  @JsonProperty("coupon_codes")
  private List<String> couponCodes;

}
