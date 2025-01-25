package com.zogg.zoggservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zogg.zoggservice.enums.VoucherTypeEnum;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class VoucherRequestDto {

    private String id;

    private String name;

    private String description;

    @JsonProperty("media_details")
    private List<MediaDetails> mediaDetails;

    @JsonProperty("coins_to_redeem")
    private Integer coinsToRedeem;

    @JsonProperty("voucher_type")
    private VoucherTypeEnum voucherType;

    @JsonProperty("voucher_description")
    private String voucherDescription;

    @JsonProperty("voucher_url")
    private String voucherUrl;

    @JsonProperty("start_date")
    private LocalDateTime startDateTime;

    @JsonProperty("end_date")
    private LocalDateTime endDateTime;

    @JsonProperty("terms_and_conditions")
    private List<String> termsAndConditions;

    @JsonProperty("how_to_avail")
    private List<String> howToAvail;

    @JsonProperty("usage_limit")
    private Long usageLimit;

    @JsonProperty("used_limit")
    private Long usedCount;

    @JsonProperty("brand_id")
    private String brandId;

    private boolean active = true;
}
