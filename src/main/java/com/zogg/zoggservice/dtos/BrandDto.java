package com.zogg.zoggservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zogg.zoggservice.enums.BusinessCategoryEnum;
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
public class BrandDto {

    private String id;

    private String name;

    private String description;

    @JsonProperty("media_details")
    private List<MediaDetails> mediaDetails;

    @JsonProperty("website_url")
    private String websiteUrl;

    @JsonProperty("business_category")
    private BusinessCategoryEnum businessCategory;

    @JsonProperty("updated_by")
    private Integer updatedBy;

    private boolean active = true;
}
