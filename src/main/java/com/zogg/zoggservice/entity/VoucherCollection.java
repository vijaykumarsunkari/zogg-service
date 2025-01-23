package com.zogg.zoggservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zogg.zoggservice.dtos.MediaDetails;
import com.zogg.zoggservice.enums.DiscountTypeEnum;
import com.zogg.zoggservice.enums.VoucherTypeEnum;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("voucher-collection")
public class VoucherCollection {

    @Id private String id;

    @NotNull
    @Indexed
    @JsonProperty("brand_id")
    private String brandId;

    private String name;

    private String description;

    @JsonProperty("voucher_url")
    private String voucherUrl;

    @JsonProperty("brand_website")
    private String brandWebsite;

    @JsonProperty("start_date_time")
    private LocalDateTime startDateTime;

    @JsonProperty("end_date_time")
    private LocalDateTime endDateTime;

    @JsonProperty("discount_type")
    private DiscountTypeEnum discountType;

    @JsonProperty("discount_value")
    private Long discountValue;

    @JsonProperty("value_x")
    private Long valueX;

    @JsonProperty("value_y")
    private Long valueY;

    @JsonProperty("coins_to_redeem")
    private Integer coinsToRedeem;

    @JsonProperty("voucher_type")
    private VoucherTypeEnum voucherType;

    @JsonProperty("media_details")
    private List<MediaDetails> mediaDetails;

    @JsonProperty("brand_description")
    private String brandDescription;

    @JsonProperty("usage_limit")
    private Long usageLimit;

    @JsonProperty("used_count")
    private Long usedCount;

    @Builder.Default private boolean active = true;

    @JsonProperty("terms_and_conditions")
    private List<String> termsAndConditions;

    @JsonProperty("how_to_avail")
    private List<String> howToAvail;

    @CreatedDate private LocalDateTime createdAt;

    @LastModifiedDate private LocalDateTime updatedAt;
}
