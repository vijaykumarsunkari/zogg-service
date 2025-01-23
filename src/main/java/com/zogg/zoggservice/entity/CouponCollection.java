package com.zogg.zoggservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
@Document("coupon-collection")
public class CouponCollection {

    @Id private String id;

    @NotNull
    @Indexed(unique = true)
    @JsonProperty("voucher_id")
    private String voucherId;

    @NotNull
    @Indexed
    @JsonProperty("coupon_code")
    private String couponCode;

    @NotNull
    @Indexed
    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("coins_used")
    private Integer coinsUsed;

    @Builder.Default private Boolean redeemed = false;

    @CreatedDate private LocalDateTime createdAt;

    @LastModifiedDate private LocalDateTime updatedAt;
}
