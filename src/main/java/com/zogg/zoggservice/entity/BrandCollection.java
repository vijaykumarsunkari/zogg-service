package com.zogg.zoggservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zogg.zoggservice.dtos.MediaDetails;
import com.zogg.zoggservice.enums.BusinessCategoryEnum;
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

@Document("brand-collection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandCollection {

    @Id private String id;

    @NotNull @Indexed private String name;

    private String description;

    @JsonProperty("media_details")
    private List<MediaDetails> mediaDetails;

    @NotNull
    @JsonProperty("website_url")
    private String websiteUrl;

    @NotNull
    @JsonProperty("business_category")
    private BusinessCategoryEnum businessCategory;

    @Builder.Default @Indexed private boolean active = true;

    @JsonProperty("updated_by")
    private Integer updatedBy;

    @CreatedDate private LocalDateTime createdAt;

    @LastModifiedDate private LocalDateTime updatedAt;
}
