package com.zogg.zoggservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class MediaDetails {

    @JsonProperty("display_type")
    private String displayType;

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("media_url")
    private String mediaUrl;

    @JsonProperty("media_format")
    private String mediaFormat;

    @JsonProperty("media_type")
    private String mediaType;
}
