package com.zogg.zoggservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zogg.zoggservice.dtos.MediaDetails;
import com.zogg.zoggservice.enums.VoucherTypeEnum;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
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

    @JsonProperty("pre_offer_description")
    private String preOfferDescription;

    @JsonProperty("full_offer_description")
    private String fullOfferDesc;

    @JsonProperty("voucher_url")
    private String voucherUrl;

    @JsonProperty("start_date_time")
    private LocalDate startDateTime;

    @JsonProperty("end_date_time")
    private LocalDate endDateTime;

    @JsonProperty("coins_to_redeem")
    private Long coinsToRedeem;

    @JsonProperty("voucher_type")
    private VoucherTypeEnum voucherType;

    @JsonProperty("media_details")
    private List<MediaDetails> mediaDetails;

    @JsonProperty("usage_limit")
    private Long usageLimit;

    @JsonProperty("used_count")
    private Long usedCount;

    @Builder.Default private boolean active = true;

    @JsonProperty("terms_and_conditions")
    private List<String> termsAndConditions;

    @JsonProperty("how_to_avail")
    private List<String> howToAvail;

    @JsonProperty("created_by")
    private Integer createdBy;

    @JsonProperty("updated_by")
    private Integer updatedBy;

    @CreatedDate private LocalDateTime createdAt;

    @LastModifiedDate private LocalDateTime updatedAt;
}
